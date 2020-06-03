package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.utils.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class AdminService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    final StudentMapper studentMapper;
    final UserService userService;


    @Autowired
    AdminService(StudentMapper studentMapper, HttpSession httpSession, UserService userService){
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    public Response addStudent(NewStudent newStudent) throws NullPointerException, PermissionException {
        String accessToken = newStudent.getAccessToken();
        userService.permission(accessToken,"Admin");
        Student student = newStudent.convertStudent();
        if(studentMapper.insert(student)==1)
            return new Response(204);
        return new Response(202,Message.DB_NOT_OPERATION);
    }
}
