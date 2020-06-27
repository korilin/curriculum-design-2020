package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.ApplyManage;
import com.arukione.curriculum_design.model.DTO.Request.TopicInfo;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.DTO.Response.TopicTResponse;
import com.arukione.curriculum_design.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class TeacherController {

    final TeacherService teacherService;


    @Autowired
    TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher/changeInfo")
    public Response changeInfo(@RequestParam("accessToken")String accessToken,
                               @RequestParam("phone")String phone,
                               @RequestParam("email")String email,
                               @RequestParam("topicDemand") String topicDemand){
        return teacherService.changeInfo(accessToken, phone,  email, topicDemand);
    }

    @PostMapping("addTopic")
    public Response addTopic(@RequestParam("accessToken")String accessToken, @RequestBody TopicInfo topicInfo){
        return teacherService.addTopic(accessToken, topicInfo);
    }

    @GetMapping("getTeacherTopic")
    public TopicTResponse getTeacherTopic(@RequestParam("accessToken") String accessToken){
        return (TopicTResponse) teacherService.getTopicT(accessToken);
    }

    @PostMapping("changeTopicInfo")
    public Response changeTopicInfo(@RequestParam("accessToken") String accessToken,
                                    @RequestParam("key") String key,
                                    @RequestParam("value") String value,
                                    @RequestParam("topicID") String id){
        return teacherService.changeTopicInfo(accessToken, key, value, id);
    }

    @DeleteMapping("deleteTeacherTopic")
    public Response deleteTeacherTopic(@RequestParam("accessToken") String accessToken, @RequestParam("topicID") String id){
        return teacherService.deleteTopic(accessToken, id);
    }

    //获取指导学生信息
    @GetMapping("getGuideStudentInfo")
    public Response getGuideStudentInfo(@RequestParam("accessToken") String accessToken){
        return teacherService.getGuideStudentInfo(accessToken);
    }

    //获取申请处理记录 √
    @GetMapping("getApplicationStatus")
    public Response getApplicationStatus(@RequestParam("accessToken") String accessToken){
        return teacherService.getApplicationStatus(accessToken);
    }

    //获取未处理的学生申请记录 √
    @GetMapping("getStudentApply")
    public Response getStudentApplication(@RequestParam("accessToken") String accessToken){
        return teacherService.getStudentApplication(accessToken);
    }

    @PostMapping("applyManage")
    public Response applyManage(@RequestParam("accessToken") String accessToken, @RequestBody ApplyManage applyManage){
        return teacherService.applyManage(accessToken, applyManage.getSid(), applyManage.getTopicId(), applyManage.getStatus());
    }
}
