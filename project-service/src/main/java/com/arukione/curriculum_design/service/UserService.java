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
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final ProfessionMapper professionMapper;
    final TopicInfoMapper topicInfoMapper;
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    UserService(AdminMapper adminMapper,
                StudentMapper studentMapper, TeacherMapper teacherMapper,
                ProfessionMapper professionMapper, TopicInfoMapper topicInfoMapper,
                TopicTypeMapper topicTypeMapper) {
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.professionMapper = professionMapper;
        this.topicInfoMapper = topicInfoMapper;
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

    public Response opsResult(int i) {
        return i == 1 ? new Response(HTTPStatus.Success) : new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
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
                    studentN.setPassword(password);
                    return opsResult(studentMapper.updateById(studentN));
                case "Teacher":
                    Teacher teacher = (Teacher) user;
                    Teacher teacherN = new Teacher();
                    teacherN.setTid(teacher.getTid());
                    teacherN.setPassword(password);
                    return opsResult(teacherMapper.updateById(teacherN));
                case "Admin":
                    Admin admin = (Admin) user;
                    Admin adminN = new Admin();
                    adminN.setAdminId(admin.getAdminId());
                    adminN.setPassword(password);
                    return opsResult(adminMapper.updateById(adminN));
                default:
                    throw new Exception("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

    public TypeResponse getTopicType() {
        ArrayList<TopicType> topicTypes = (ArrayList<TopicType>) topicTypeMapper.selectList(null);
        return new TypeResponse(HTTPStatus.OK, topicTypes);
    }


}
