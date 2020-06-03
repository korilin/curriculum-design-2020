package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.NewStudent;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.service.AdminService;
import com.arukione.curriculum_design.utils.Message;
import com.arukione.curriculum_design.utils.PermissionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    final AdminService adminService;

    AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("addStudent")
    public Response addStudent(@RequestBody NewStudent newStudent) throws PermissionException{
        try {
            return adminService.addStudent(newStudent);
        }
        catch (NullPointerException npe){
            return new Response(401, Message.STATUS_FAILURE_MESSAGE);
        }
    }

}
