package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.*;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.*;
import com.arukione.curriculum_design.utils.Generator;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final UserService userService;
    final TeacherMapper teacherMapper;
    final TopicInfoMapper topicInfoMapper;
    final ApplicationMapper applicationMapper;
    final StudentMapper studentMapper;
    final ProfessionMapper professionMapper;
    final DepartmentMapper departmentMapper;
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    TeacherService(UserService userService, TeacherMapper teacherMapper,
                   ApplicationMapper applicationMapper,StudentMapper studentMapper,
                   ProfessionMapper professionMapper,DepartmentMapper departmentMapper,
                   TopicInfoMapper topicInfoMapper,TopicTypeMapper topicTypeMapper) {
        this.userService = userService;
        this.teacherMapper = teacherMapper;
        this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper=applicationMapper;
        this.studentMapper=studentMapper;
        this.professionMapper=professionMapper;
        this.departmentMapper=departmentMapper;
        this.topicTypeMapper=topicTypeMapper;
    }

    Response teacherPermission(String accessToken) {
        try {
            userService.permission(accessToken, "Teacher");
            return null;
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public Response changeInfo(String accessToken, String phone, String email, String topicDemand) {
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            Teacher teacherN = Teacher.builder()
                    .tid(teacher.getTid())
                    .phone(phone)
                    .email(email)
                    .topicDemand(topicDemand)
                    .build();
            int i = teacherMapper.updateById(teacherN);
            if (i == 1) {
                teacher.setPhone(phone);
                teacher.setEmail(email);
                teacher.setTopicDemand(topicDemand);
                redisTemplate.opsForValue().set(accessToken, teacher, 1, TimeUnit.DAYS);
            }
            return userService.updateResult(i);
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public Response addTopic(String accessToken, TopicInfo topicInfo){
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            Topic topic = Topic.builder()
                    .topicId(Generator.generateTopicID())
                    .topicName(topicInfo.getTopicName())
                    .introduction(topicInfo.getIntroduction())
                    .tid(teacher.getTid())
                    .typeId(topicInfo.getType())
                    .source("0")
                    .build();
            return userService.updateResult(topicInfoMapper.insert(topic));
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public ArrayList<Object> getStudentApply(){
        ArrayList<Object> response = new ArrayList<Object>();

        //获取所有未处理申请数据的学生id与课题id及时间
        ArrayList<Application> apps = applicationMapper.getApplicationsOfStatus();

        //循环获取所有详细信息
        for(int i=0;i<apps.size();i++){
            ArrayList<Object> temp = new ArrayList<Object>();
            Application app = apps.get(i);

            //获得studentInfo
            Student stu = studentMapper.getStudent(app.getSid());//获得sname,proid
            Profession pro = professionMapper.getProfession(stu.getProfessionId());//获得proname,depid
            Department dep = departmentMapper.getDepartmen(pro.getDeptId());//获得depname

            //获得topicInfo
            Topic topic = topicInfoMapper.getTopic(app.getTopicId());//获得topicname,introduction,Source

            TopicType type = topicTypeMapper.getTopicType(topic.getTypeId());//获得typename

            //添加List
            temp.add(app.getApplyTime());
            temp.add(stu);
            temp.add(pro);
            temp.add(dep);
            temp.add(topic);
            temp.add(type);
            response.add(temp);
        }
        return response;
    }

}
