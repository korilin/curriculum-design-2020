package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.mapper.ApplicationMapper;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Request.NewTeacher;
import com.arukione.curriculum_design.model.DTO.Response.AccountResponse;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.*;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.exception.PermissionException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    final StudentMapper studentMapper;
    final TeacherMapper teacherMapper;
    final UserService userService;
    final TopicInfoMapper topicInfoMapper;
    final ApplicationMapper applicationMapper;


    @Autowired
    AdminService(StudentMapper studentMapper, TeacherMapper teacherMapper, UserService userService, TopicInfoMapper topicInfoMapper, ApplicationMapper applicationMapper) {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
        this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper = applicationMapper;
    }

    public Response adminPermission(String accessToken) {
        try {
            userService.permission(accessToken, "Admin");
            return null;
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public Response addStudent(NewStudent newStudent) {
        Response response = adminPermission(newStudent.getAccessToken());
        if (response != null) return response;
        Student student = newStudent.convertStudent();
        try {
            if (studentMapper.insert(student) == 1)
                return new Response(HTTPStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, "该学号已有学生用户使用");
        }

        return new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
    }

    public Response addTeacher(NewTeacher newTeacher) throws DataIntegrityViolationException {
        Response response = adminPermission(newTeacher.getAccessToken());
        if (response != null) return response;
        Teacher teacher = newTeacher.convertTeacher();
        System.out.println(teacher);
        try {
            if (teacherMapper.insert(teacher) == 1)
                return new Response(HTTPStatus.Success);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, "该工号已有教师用户使用");
        }
        return new Response(HTTPStatus.Failed, Message.DB_NOT_OPERATION);
    }

    public Response getAccounts(String accessToken) {
        Response response = adminPermission(accessToken);
        if (response != null) return response;
        List<Student> students = userService.getStudents(null);
        List<Teacher> teachers = userService.getTeachers(null);
        return new AccountResponse(HTTPStatus.OK, students, teachers);
    }

    public Response deleteStudentByID(String accessToken, String SID) {
    try {
        return opsResult(accessToken, studentMapper.deleteById(SID), "删除失败");
    }catch (Exception e){
        if(e.getMessage().contains("foreign key")){
            topicInfoMapper.updateSID(SID);
            QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();
            applicationQueryWrapper.eq("SID", SID);
            applicationMapper.delete(applicationQueryWrapper);
            return opsResult(accessToken, studentMapper.deleteById(SID), "删除失败");
        }else {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }
    }

    public Response deleteTeacherByID(String accessToken, String TID) {
        try {
            return opsResult(accessToken, teacherMapper.deleteById(TID), "删除失败");
        } catch (Exception e) {
            if(e.getMessage().contains("foreign key")){
                QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
                topicQueryWrapper.eq("TID", TID);
                QueryWrapper<Application> applicationQueryWrapper = new QueryWrapper<>();
                List<Topic> topics = topicInfoMapper.selectList(topicQueryWrapper);
                for(Topic topic: topics){
                    applicationQueryWrapper.eq("TopicID", topic.getTopicId());
                    applicationQueryWrapper.or();
                }
                applicationMapper.delete(applicationQueryWrapper);
                topicInfoMapper.delete(topicQueryWrapper);
                return opsResult(accessToken, teacherMapper.deleteById(TID), "删除失败");
            }else {
                e.printStackTrace();
                return new Response(HTTPStatus.Failed, e.getMessage());
            }
        }
    }

    private Response opsResult(String accessToken, int i, String message) {
        Response response = adminPermission(accessToken);
        if (response != null) return response;
        if (i == 1)
            return new Response(HTTPStatus.Success);
        else return new Response(HTTPStatus.Failed, message);
    }

    public Response changeStudentByID(String accessToken, String sid, String key, String value) {
        try {
            Student student = new Student();
            if (!key.equals("sid")) {
                student.setValue(key, value);
                student.setValue("sid", sid);
                return opsResult(accessToken, studentMapper.updateById(student), "修改失败");
            } else {
                return opsResult(accessToken, studentMapper.updateSID(value, sid), "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

    public Response changeTeacherByID(String accessToken, String tid, String key, String value) {
        try {
            Teacher teacher = new Teacher();
            if (!key.equals("tid")) {
                teacher.setValue(key, value);
                teacher.setValue("tid", tid);
                return opsResult(accessToken, teacherMapper.updateById(teacher), "修改失败");
            } else {
                return opsResult(accessToken, teacherMapper.updateTID(value, tid), "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

}

