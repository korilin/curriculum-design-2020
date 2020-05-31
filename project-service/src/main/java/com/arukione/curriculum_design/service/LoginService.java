package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.model.entity.Admin;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.Teacher;
import com.arukione.curriculum_design.utils.Generator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    final HttpSession httpSession;
    final AdminMapper adminMapper;
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;

    @Autowired
    LoginService(HttpSession httpSession, AdminMapper adminMapper, StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.httpSession = httpSession;
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public String studentLogin(String id, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SID", id).eq("Password", password);
        if (studentMapper.selectOne(queryWrapper) != null)
            return Generator.generateAccessToken();
        return null;
    }

    public String teacherLogin(String id, String password) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SID", id).eq("Password", password);
        if (teacherMapper.selectOne(queryWrapper) != null)
            return Generator.generateAccessToken();
        return null;
    }

    public String adminLogin(String id, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("AdminID", id).eq("Password", password);
        if (adminMapper.selectOne(queryWrapper) != null)
            return Generator.generateAccessToken();
        return null;
    }

    public void removeAccessToken(String accessToken){
        redisTemplate.delete(accessToken);
    }

}
