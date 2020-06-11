package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Response.SelectableTeacherResponse;
import com.arukione.curriculum_design.model.DTO.Response.SelectableTopicResponse;
import com.arukione.curriculum_design.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getSelectableTopic")
    public SelectableTopicResponse getSelectableTopic(@RequestParam("accessToken") String accessToken){
        return studentService.getAllTopic(accessToken);
    }

    @GetMapping("/getSelectableTeacher")
    public SelectableTeacherResponse getSelectableTeacher(@RequestParam("accessToken") String accessToken){
        return studentService.getAllTeacher(accessToken);
    }

}
