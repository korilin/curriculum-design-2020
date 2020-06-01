package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.LoginRequest;
import com.arukione.curriculum_design.model.DTO.Response.BaseInfoResponse;
import com.arukione.curriculum_design.model.DTO.Response.LoginResponse;
import com.arukione.curriculum_design.model.DTO.Response.Response;
import com.arukione.curriculum_design.service.LoginService;
import org.apache.ibatis.type.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class LoginController {

    final LoginService loginService;

    @Autowired
    LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        String userType = loginRequest.getUserType();
        String id = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        switch (userType) {
            case "Student":
                return loginService.studentLogin(id, password);
            case "Teacher":
                return loginService.teacherLogin(id, password);
            case "Admin":
                return loginService.adminLogin(id, password);
            default:
                throw new Exception("SPA页面userType数据异常");
        }
    }

    @GetMapping("logout")
    public Response logout(@RequestParam("access_token") String accessToken) {
        loginService.removeAccessToken(accessToken);
        return new Response(300);
    }

    @GetMapping("baseInfo")
    public BaseInfoResponse baseInfo(@RequestParam("access_token") String accessToken) {
        return loginService.getBaseInfo(accessToken);
    }
}
