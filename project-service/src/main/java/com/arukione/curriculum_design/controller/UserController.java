package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.LoginRequest;
import com.arukione.curriculum_design.model.DTO.Response.BaseInfoResponse;
import com.arukione.curriculum_design.model.DTO.Response.LoginResponse;
import com.arukione.curriculum_design.model.DTO.Response.ProfessionResponse;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.model.entity.Profession;
import com.arukione.curriculum_design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class UserController {

    final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        String userType = loginRequest.getUserType();
        String id = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        switch (userType) {
            case "Student":
                return userService.studentLogin(id, password);
            case "Teacher":
                return userService.teacherLogin(id, password);
            case "Admin":
                return userService.adminLogin(id, password);
            default:
                throw new Exception("SPA页面userType数据异常");
        }
    }

    @GetMapping("logout")
    public Response logout(@RequestParam("access_token") String accessToken) {
        userService.removeAccessToken(accessToken);
        return new Response(204);
    }

    @GetMapping("getProfessions")
    public ProfessionResponse getProfessions(){
        ArrayList<Profession> professions = userService.getProfessions();
        return new ProfessionResponse(200,professions);
    }

    @GetMapping("baseInfo")
    public BaseInfoResponse baseInfo(@RequestParam("access_token") String accessToken) {
        return userService.getBaseInfo(accessToken);
    }

}
