package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends Response{
    String accessToken;
    public LoginResponse(int status, String accessToken){
        super(status);
        this.accessToken = accessToken;
    }
}
