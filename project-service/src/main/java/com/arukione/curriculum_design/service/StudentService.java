package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTeacherResponse;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTopicResponse;
import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.Teacher;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    final UserService userService;
    final TopicInfoMapper topicInfoMapper;
    final TeacherMapper teacherMapper;

    @Autowired
    public StudentService(UserService userService, TopicInfoMapper topicInfoMapper, TeacherMapper teacherMapper) {
        this.userService = userService;
        this.topicInfoMapper = topicInfoMapper;
        this.teacherMapper = teacherMapper;
    }

    Response studentPermission(String accessToken) {
        try {
            userService.permission(accessToken, "Student");
            return null;
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public SelectableTopicResponse getAllTopic(String accessToken){
        try {
            Student student = (Student) userService.permission(accessToken, "Student");
            String profId = student.getProfessionId();
            ArrayList<SelectableTopicInfo> selectableTopicInfos = topicInfoMapper.getSelectableTopicInfo(profId);
            return new SelectableTopicResponse(HTTPStatus.OK,selectableTopicInfos);
        } catch (PermissionException permissionException) {
            return new SelectableTopicResponse(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new SelectableTopicResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public SelectableTeacherResponse getAllTeacher(String accessToken) {
        try {
            Student student = (Student) userService.permission(accessToken, "Student");
            String profId = student.getProfessionId();
            QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
            teacherQueryWrapper.eq("GuideProfID", profId);
            List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
            return new SelectableTeacherResponse(HTTPStatus.OK, teachers);
        }catch (PermissionException permissionException) {
            return new SelectableTeacherResponse(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new SelectableTeacherResponse(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

}
