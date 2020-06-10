package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;
import com.arukione.curriculum_design.mapper.StudentMapper;
import com.arukione.curriculum_design.mapper.TeacherMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.mapper.TopicTypeMapper;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.TopicTResponse;
import com.arukione.curriculum_design.model.VO.TopicView;
import com.arukione.curriculum_design.model.entity.*;
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
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    TeacherService(UserService userService, StudentMapper studentMapper, TeacherMapper teacherMapper, TopicInfoMapper topicInfoMapper, TopicTypeMapper topicTypeMapper) {
        this.userService = userService;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.topicInfoMapper = topicInfoMapper;
        this.topicTypeMapper = topicTypeMapper;
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

    public Response addTopic(String accessToken, TopicInfo topicInfo) {
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("TID", teacher.getTid());
            List<Topic> topics = topicInfoMapper.selectList(queryWrapper);
            if(topics.size()>=20) return new Response(HTTPStatus.Failed, "您已有的课题已经达到了20个，无法继续添加");
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
            ArrayList<TopicView> topicViews = topicInfoMapper.getTopicN(tid);
            for (TopicView topic : topicViews) {
                String sid = topic.getSID();
                if (sid == null) break;
                Student student = studentMapper.selectById(sid);
                topic.setSName(student.getName());
            }
            return new TopicTResponse(HTTPStatus.OK, topicViews);
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public Response changeTopicInfo(String accessToken, String key, String value, String id) {
        Response response = teacherPermission(accessToken);
        if (response != null) return response;
        Topic topic = new Topic();
        try {
            if (key.equals("type")){
                QueryWrapper<TopicType> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("TypeName", value);
                List<TopicType> topicTypes = topicTypeMapper.selectList(queryWrapper);
                value = topicTypes.get(0).getTypeId();
            }
            topic.setValue(key, value);
            topic.setTopicId(id);
            return userService.updateResult(topicInfoMapper.updateById(topic));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

    public Response deleteTopic(String accessToken, String id){
        Response response = teacherPermission(accessToken);
        if (response != null) return response;
        return userService.updateResult(topicInfoMapper.deleteById(id));
    }

}
