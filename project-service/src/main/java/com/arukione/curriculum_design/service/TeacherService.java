package com.arukione.curriculum_design.service;

import com.arukione.curriculum_design.exception.PermissionException;

import com.arukione.curriculum_design.mapper.*;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.*;
import com.arukione.curriculum_design.model.VO.ApplicationStatusInfo;
import com.arukione.curriculum_design.model.VO.GuideStudentInfo;
import com.arukione.curriculum_design.model.VO.StudentApplication;
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
    final ApplicationMapper applicationMapper;
    final ProfessionMapper professionMapper;
    final DepartmentMapper departmentMapper;
    final TopicTypeMapper topicTypeMapper;

    @Autowired
    TeacherService(UserService userService, TeacherMapper teacherMapper,
                   ApplicationMapper applicationMapper,StudentMapper studentMapper,
                   ProfessionMapper professionMapper,DepartmentMapper departmentMapper,
                   TopicInfoMapper topicInfoMapper,TopicTypeMapper topicTypeMapper) {
        this.userService = userService;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.topicInfoMapper = topicInfoMapper;
        this.applicationMapper=applicationMapper;
        this.professionMapper=professionMapper;
        this.departmentMapper=departmentMapper;
        this.topicTypeMapper=topicTypeMapper;
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
            return userService.opsResult(i);
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
                    .typeId(topicInfo.getTypeId())
                    .source("0")
                    .build();
            return userService.opsResult(topicInfoMapper.insert(topic));
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    public ArrayList<Object> getStudentApply(String status) {
        ArrayList<Object> response = new ArrayList<Object>();

        //获取所有申请数据的学生id与课题id及时间
        ArrayList<Application> apps = applicationMapper.getApplicationsOfStatus(status);

        //循环获取所有详细信息
        for (int i = 0; i < apps.size(); i++) {
            ArrayList<Object> temp = new ArrayList<Object>();
            Application app = apps.get(i);

            //获得studentInfo
            Student stu = studentMapper.getStudent(app.getSid());//获得sname,proid
            Profession pro = professionMapper.getProfession(stu.getProfessionId());//获得proname,depid
            Department dep = departmentMapper.getDepartmen(pro.getDeptId());//获得depname

            //获得topicInfo
            Topic topic = topicInfoMapper.getTopic(app.getTopicId());//获得topicname,introduction,Source

            TopicType type = topicTypeMapper.getTopicType(topic.getTypeId());//获得typename

            //添加List
            temp.add(app.getApplyTime());
            temp.add(app.getStatus());
            temp.add(stu);
            temp.add(pro);
            temp.add(dep);
            temp.add(topic);
            temp.add(type);
            response.add(temp);
        }
        return response;
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
            return userService.opsResult(topicInfoMapper.updateById(topic));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response(HTTPStatus.Failed, e.getMessage());
        }
    }

    public Response deleteTopic(String accessToken, String id){
        Response response = teacherPermission(accessToken);
        if (response != null) return response;
        return userService.opsResult(topicInfoMapper.deleteById(id));
    }

    //获取指导学生信息
    public Response getGuideStudentInfo(String accessToken){
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            String tid = teacher.getTid();
            ArrayList<GuideStudentInfo> gsInfo= studentMapper.getGuideStudentInfo(tid);
            return new GuideStudentInfoResponse(200,gsInfo);
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }


    //获取申请处理记录
    public Response getApplicationStatus(String accessToken){
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            String tid = teacher.getTid();
            ArrayList<ApplicationStatusInfo> asInfo= studentMapper.getApplicationStatusInfo(tid);
            return new ApplicationStatusInfoResponse(200,asInfo);
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }

    //获取未处理的学生申请
    public Response getStudentApplication(String accessToken){
        try {
            Teacher teacher = (Teacher) userService.permission(accessToken, "Teacher");
            String tid = teacher.getTid();
            ArrayList<StudentApplication> saInfo= studentMapper.getStudentApplication(tid);
            return new StudentApplicationResponse(200,saInfo);
        } catch (PermissionException permissionException) {
            return new Response(HTTPStatus.NotAllowed, Message.USER_PERMISSION_ERROR);
        } catch (NullPointerException npe) {
            return new Response(HTTPStatus.Unauthorized, Message.NO_LOGIN_STATUS);
        }
    }
}
