package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.*;
import com.arukione.curriculum_design.model.DTO.Response.*;
import com.arukione.curriculum_design.model.entity.*;
import com.arukione.curriculum_design.utils.Generator;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.exception.PermissionException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final AdminMapper adminMapper;
    final ApplicationMapper applictionMapper;
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final ProfessionMapper professionMapper;
    final TopicInfoMapper topicInfoMapper;
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    UserService(AdminMapper adminMapper,ApplicationMapper applictionMapper,
                StudentMapper studentMapper, TeacherMapper teacherMapper,
                ProfessionMapper professionMapper, TopicInfoMapper topicInfoMapper,
                TopicTypeMapper topicTypeMapper) {
        this.adminMapper = adminMapper;
        this.applictionMapper=applictionMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.professionMapper = professionMapper;
        this.topicInfoMapper=topicInfoMapper;
        this.topicTypeMapper = topicTypeMapper;
    }

    public LoginResponse studentLogin(String id, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SID", id).eq("Password", password);
        Student student = studentMapper.selectOne(queryWrapper);
        String accessToken = null;
        if (student != null) {
            accessToken = Generator.generateAccessToken();
            student.setUserType("Student");
            redisTemplate.opsForValue().set(accessToken, student, 1, TimeUnit.DAYS);
            return new LoginResponse(HTTPStatus.Success, accessToken, "Student", student.getName());
        }
        return new LoginResponse(HTTPStatus.Failed, null, Message.ID_OR_PASSWORD_ERROR);
    }

    public LoginResponse teacherLogin(String id, String password) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TID", id).eq("Password", password);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        String accessToken = null;
        if (teacher != null) {
            accessToken = Generator.generateAccessToken();
            teacher.setUserType("Teacher");
            redisTemplate.opsForValue().set(accessToken, teacher, 1, TimeUnit.DAYS);
            return new LoginResponse(HTTPStatus.Success, accessToken, "Teacher", teacher.getName());
        }
        return new LoginResponse(HTTPStatus.Failed, null, Message.ID_OR_PASSWORD_ERROR);
    }

    public LoginResponse adminLogin(String id, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("AdminID", id).eq("Password", password);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin != null) {
            String accessToken = Generator.generateAccessToken();
            admin.setUserType("Admin");
            admin.setName(admin.getAdminId());
            redisTemplate.opsForValue().set(accessToken, admin, 1, TimeUnit.DAYS);
            return new LoginResponse(HTTPStatus.Success, accessToken, "Admin", admin.getName());
        }
        return new LoginResponse(HTTPStatus.Failed, null, Message.ID_OR_PASSWORD_ERROR);
    }

    public void removeAccessToken(String accessToken) {
        redisTemplate.delete(accessToken);
    }

    public ArrayList<Profession> getProfessions() {
        List<Profession> professions = professionMapper.getProfessionsIncludeDepartment();
        return (ArrayList<Profession>) professions;
    }

    public BaseInfoResponse getBaseInfo(String accessToken) {
        User user = redisTemplate.opsForValue().get(accessToken);
        if (user == null)
            return new BaseInfoResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        String userType = user.getUserType();
        String name = user.getName();
        return new BaseInfoResponse(HTTPStatus.OK, userType, name);
    }

    public User permission(String accessToken, String userType) throws PermissionException {
        User user = redisTemplate.opsForValue().get(accessToken);
        if (user == null) throw new NullPointerException();
        if (!user.getUserType().equals(userType)) throw new PermissionException();
        return user;
    }

    public List<Student> getStudents(String professionId) {
        QueryWrapper<Student> queryWrapper = null;
        if (professionId != null) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ProfID", professionId);
        }
        return studentMapper.selectList(queryWrapper);
    }

    public List<Teacher> getTeachers(String professionId) {
        QueryWrapper<Teacher> queryWrapper = null;
        if (professionId != null) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ProfID", professionId);
        }
        return teacherMapper.selectList(queryWrapper);
    }

    public UserInfoResponse getUserInfo(String accessToken) {
        User user = redisTemplate.opsForValue().get(accessToken);
        if (user == null) return new UserInfoResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        String userType = user.getUserType();
        switch (userType) {
            case "Student":
                return new UserInfoResponse(HTTPStatus.OK, (Student) user);
            case "Teacher":
                return new UserInfoResponse(HTTPStatus.OK, (Teacher) user);
            case "Admin":
                return new UserInfoResponse(HTTPStatus.OK, (Admin) user);
            default:
                return new UserInfoResponse(HTTPStatus.Failed, "找不到用户类型");
        }
    }

    public Response updateResult(int i){
       return i==1? new Response(HTTPStatus.Success):new Response(HTTPStatus.Failed, "修改失败");
    }

    public Response changePassword(String accessToken, String password) {
        User user = redisTemplate.opsForValue().get(accessToken);
        if (user == null)
            return new BaseInfoResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        String userType = user.getUserType();
        try {
            switch (userType) {
                case "Student":
                    Student student = (Student) user;
                    Student studentN = new Student();
                    studentN.setSid(student.getSid());
                    student.setPassword(password);
                    return updateResult(studentMapper.updateById(studentN));
                case "Teacher":
                    Teacher teacher = (Teacher) user;
                    Teacher teacherN = new Teacher();
                    teacherN.setTid(teacher.getTid());
                    teacher.setPassword(password);
                    return updateResult(teacherMapper.updateById(teacherN));
                case "Admin":
                    Admin admin = (Admin) user;
                    Admin adminN = new Admin();
                    adminN.setAdminId(admin.getAdminId());
                    adminN.setPassword(password);
                    return updateResult(adminMapper.updateById(adminN));
                default: throw new Exception("Error");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

    public TypeResponse getTopicType(){
        ArrayList<TopicType> topicTypes = (ArrayList<TopicType>) topicTypeMapper.selectList(null);
        return new TypeResponse(HTTPStatus.OK, topicTypes);
    }

    //获取我的导师信息
    public UserInfoResponse getMyTeacher(String accessToken){
        Student stu = (Student)redisTemplate.opsForValue().get(accessToken);

        //获取已通过的申请记录
        ArrayList<Application> app = applictionMapper.getApplicationsOfSIDAndStatus(stu.getSid(),"1");
        if(app==null) return null;

        //获取所需导师信息
        Topic topic = topicInfoMapper.getTopic(app.get(0).getTopicId());
        return new UserInfoResponse(200,teacherMapper.getTeacher(topic.getTid()));
    }

    //获取所有导师信息
    public ArrayList<Teacher> getAllTeacher(){
        return teacherMapper.getAllTeacher();
    }

    //获得已通过的课题信息
    public ArrayList<Object> getAllowTopic(String accessToken){
        Student stu = (Student)redisTemplate.opsForValue().get(accessToken);

        //获取已通过的申请记录
        ArrayList<Application> app = applictionMapper.getApplicationsOfSIDAndStatus(stu.getSid(),"1");
        if(app==null) return null;

        //获取所需的课题信息
        Topic topic = topicInfoMapper.getTopic(app.get(0).getTopicId());
        TopicType type = topicTypeMapper.getTopicType(topic.getTypeId());

        //添加课题信息
        ArrayList<Object> response = new ArrayList<Object>();
        response.add(topic);
        response.add(type);

        return response;
    }

    //获取申请记录
    public ArrayList<Object> getApplicationInfo(String accessToken){
        //Student stu = (Student)redisTemplate.opsForValue().get(accessToken);

        //获取申请记录信息
        ArrayList<Application> app = applictionMapper.getApplicationsOfSID("201835020820");
        if(app==null) return null;
        ArrayList<Object> response = new ArrayList<Object>();

        //循环所有申请记录
        for (int i=0;i<app.size();i++){
            ArrayList<Object> temp = new ArrayList<Object>();
            Topic topic = topicInfoMapper.getTopic(app.get(i).getTopicId());
            Teacher teacher = teacherMapper.getTeacher(topic.getTid());
            TopicType type = topicTypeMapper.getTopicType(topic.getTypeId());

            //添加单个记录信息
            temp.add(app.get(i));
            temp.add(topic);
            temp.add(teacher);
            temp.add(type);

            //单个记录信息添加到List
            response.add(temp);
        }
        return response;
    }
}
