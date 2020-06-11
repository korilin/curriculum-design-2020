package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTopicResponse;
import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.User;
import com.arukione.curriculum_design.utils.HTTPStatus;
import com.arukione.curriculum_design.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class StudentService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;
    final UserService userService;
    final TopicInfoMapper topicInfoMapper;

    @Autowired
    public StudentService(UserService userService, TopicInfoMapper topicInfoMapper) {
        this.userService = userService;
        this.topicInfoMapper = topicInfoMapper;
    }

    Response teacherPermission(String accessToken) {
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

}
