package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.model.DTO.Response.BaseInfoResponse;
import com.arukione.curriculum_design.model.DTO.Response.LoginResponse;
import com.arukione.curriculum_design.model.entity.Admin;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.Teacher;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.Generator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final HttpSession httpSession;
    final AdminMapper adminMapper;
    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;

    String STATUS_FAILURE_MESSAGE ="Landing status failure:登陆状态失效";
    String ID_OR_PASSWORD_ERROR = "账号或密码错误";

    @Autowired
    LoginService(HttpSession httpSession, AdminMapper adminMapper, StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.httpSession = httpSession;
        this.adminMapper = adminMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public LoginResponse studentLogin(String id, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SID", id).eq("Password", password);
        Student student = studentMapper.selectOne(queryWrapper);
        String accessToken = null;
        if (student != null){
            accessToken = Generator.generateAccessToken();
            student.setUserType("Student");
            redisTemplate.opsForValue().set(accessToken,student,1, TimeUnit.DAYS);
            return new LoginResponse(300,accessToken,"Student",student.getName());
        }
        return new LoginResponse(302, null, ID_OR_PASSWORD_ERROR);
    }

    public LoginResponse teacherLogin(String id, String password) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TID", id).eq("Password", password);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        String accessToken = null;
        if (teacher != null){
            accessToken = Generator.generateAccessToken();
            teacher.setUserType("Teacher");
            redisTemplate.opsForValue().set(accessToken,teacher,1, TimeUnit.DAYS);
            return new LoginResponse(300,accessToken,"Teacher",teacher.getName());
        }
        return new LoginResponse(302, null, ID_OR_PASSWORD_ERROR);
    }

    public LoginResponse adminLogin(String id, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("AdminID", id).eq("Password", password);
        Admin admin = adminMapper.selectOne(queryWrapper);
        String accessToken = null;
        if (admin != null){
            accessToken = Generator.generateAccessToken();
            admin.setUserType("Admin");
            admin.setName(admin.getAdminId());
            redisTemplate.opsForValue().set(accessToken,admin,1, TimeUnit.DAYS);
            return new LoginResponse(300,accessToken,"Admin",admin.getName());
        }
        return new LoginResponse(302, null, ID_OR_PASSWORD_ERROR);
    }

    public void removeAccessToken(String accessToken) {
        redisTemplate.delete(accessToken);
    }

    public BaseInfoResponse getBaseInfo(String accessToken){
        User user = redisTemplate.opsForValue().get(accessToken);
        if(user==null)
            return new BaseInfoResponse(302, STATUS_FAILURE_MESSAGE);
        String userType = user.getUserType();
        String name = user.getName();
        return new BaseInfoResponse(200,userType,name);
    }

}
