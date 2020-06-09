package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.Teacher;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final UserService userService;
    final TeacherMapper teacherMapper;

    @Autowired
    TeacherService(UserService userService, TeacherMapper teacherMapper) {
        this.userService = userService;
        this.teacherMapper = teacherMapper;
    }

    Response teacherPermission(String accessToken) {
        try {
            userService.permission(accessToken, "Admin");
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

}
