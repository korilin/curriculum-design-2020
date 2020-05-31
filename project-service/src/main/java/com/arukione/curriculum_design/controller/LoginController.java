package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.LoginRequest;
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
        String accessToken;
        int status = 200;
        switch (userType) {
            case "Student":
                accessToken = loginService.studentLogin(id, password);
                break;
            case "Teacher":
                accessToken = loginService.teacherLogin(id, password);
                break;
            case "Admin":
                accessToken = loginService.adminLogin(id, password);
                break;
            default:
                throw new Exception("SPA页面userType数据异常");
        }
        if (accessToken == null)
            status = 202;
        return new LoginResponse(status, accessToken);
    }

    @PostMapping("logout")
    public Response logout(@RequestBody Map<String, String> tokenMap) throws Exception {
        String accessToken = tokenMap.get("accessToken");
        loginService.removeAccessToken(accessToken);
        return new Response(300);
    }
}
