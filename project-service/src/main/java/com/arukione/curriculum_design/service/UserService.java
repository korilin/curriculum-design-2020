package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.mapper.ProfessionMapper;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.model.DTO.Response.BaseInfoResponse;
import com.arukione.curriculum_design.model.DTO.Response.LoginResponse;
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


    @Autowired
    UserService(AdminMapper adminMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, ProfessionMapper professionMapper) {
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.professionMapper = professionMapper;
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
        return new LoginResponse( HTTPStatus.Failed, null, Message.ID_OR_PASSWORD_ERROR);
    }

    public void removeAccessToken(String accessToken) {
        redisTemplate.delete(accessToken);
    }

    public ArrayList<Profession> getProfessions() {
        List<Profession> professions = professionMapper.selectList(null);
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

}
