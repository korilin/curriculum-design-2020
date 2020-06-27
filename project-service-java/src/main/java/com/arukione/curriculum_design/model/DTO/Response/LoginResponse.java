package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends BaseInfoResponse{
    String accessToken;
    public LoginResponse(int status, String accessToken,String userType,String name){
        super(status,userType,name);
        this.accessToken = accessToken;
    }

    public LoginResponse(int status, String accessToken,String mes){
        super(status,mes);
        this.accessToken = accessToken;
    }
}
