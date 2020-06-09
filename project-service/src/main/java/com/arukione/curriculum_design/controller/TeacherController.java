package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    final TeacherService teacherService;

    @Autowired
    TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("changeInfo")
    public Response changeInfo(@RequestParam("accessToken")String accessToken,
                               @RequestParam("phone")String phone,
                               @RequestParam("email")String email,
                               @RequestParam("topicDemand") String topicDemand){
        return teacherService.changeInfo(accessToken, phone, email, topicDemand);
    }

}
