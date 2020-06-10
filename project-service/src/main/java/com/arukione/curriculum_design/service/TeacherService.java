package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.TopicTResponse;
import com.arukione.curriculum_design.model.TO.TopicT;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.Teacher;
import com.arukione.curriculum_design.model.entity.Topic;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.Generator;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final UserService userService;
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final TopicInfoMapper topicInfoMapper;

    @Autowired
    TeacherService(UserService userService, StudentMapper studentMapper, TeacherMapper teacherMapper, TopicInfoMapper topicInfoMapper) {
        this.userService = userService;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.topicInfoMapper = topicInfoMapper;
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

    public Response getTopicT(String accessToken) {
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            String tid = teacher.getTid();
            ArrayList<TopicT> topicTS = topicInfoMapper.getTopicN(tid);
            for(TopicT topic: topicTS){
                String sid = topic.getSID();
                if (sid==null) break;
                Student student = studentMapper.selectById(sid);
                topic.setSName(student.getName());
            }
            return new TopicTResponse(HTTPStatus.OK, topicTS);
        }catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

}
