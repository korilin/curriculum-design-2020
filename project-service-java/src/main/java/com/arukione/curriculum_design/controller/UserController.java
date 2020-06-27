package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.model.DTO.Request.LoginRequest;
import com.arukione.curriculum_design.model.DTO.Response.*;
import com.arukione.curriculum_design.model.entity.Profession;
import com.arukione.curriculum_design.service.UserService;
import com.arukione.curriculum_design.utils.HTTPStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
                throw new Exception("SPA User Type Error: 数据异常");
        }
    }

    @GetMapping("logout")
    public Response logout(@RequestParam("accessToken") String accessToken) {
        userService.removeAccessToken(accessToken);
        return new Response(HTTPStatus.Success);
    }

    @GetMapping("getProfessions")
    public ProfessionResponse getProfessions() {
        ArrayList<Profession> professions = userService.getProfessions();
        return new ProfessionResponse(HTTPStatus.OK, professions);
    }

    @GetMapping("baseInfo")
    public BaseInfoResponse baseInfo(@RequestParam("accessToken") String accessToken) {
        return userService.getBaseInfo(accessToken);
    }

    @GetMapping("userInfo")
    public UserInfoResponse userInfo(@RequestParam("accessToken") String accessToken) {
        return userService.getUserInfo(accessToken);
    }

    @PostMapping("changePassword")
    public Response changePassword(@RequestParam("accessToken") String accessToken, @RequestParam("password") String password) {
        return userService.changePassword(accessToken, password);
    }

    @GetMapping("getType")
    public TypeResponse getType(){
       return userService.getTopicType();
    }

    @GetMapping("/verification/code/{time}")
    public String getCode(@PathVariable("time") String time){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> code = restTemplate.getForEntity("http://127.0.0.1:5000/verification/code/"+time, String.class);
        return code.getBody();
    }
}
