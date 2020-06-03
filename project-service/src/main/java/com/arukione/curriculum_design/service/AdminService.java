package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.exception.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class AdminService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    final StudentMapper studentMapper;
    final UserService userService;


    @Autowired
    AdminService(StudentMapper studentMapper, HttpSession httpSession, UserService userService) {
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    public Response addStudent(NewStudent newStudent) throws NullPointerException {
        String accessToken = newStudent.getAccessToken();
        try {
            userService.permission(accessToken, "Admin");
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
        Student student = newStudent.convertStudent();
        try{
            if (studentMapper.insert(student) == 1)
                return new Response(HTTPStatus.Success);
        }catch (Exception e){
            return new Response(HTTPStatus.Failed, "该学号已有学生用户使用");
        }

        return new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
    }
}

