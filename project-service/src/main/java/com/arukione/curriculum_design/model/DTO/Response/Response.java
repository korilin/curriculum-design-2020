package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;

@Data
public class Response {
    int status;
    String errorMessage;

    public Response(int status){
        this.status = status;
        this.errorMessage = "";
    }

    public Response(int status, String message){
        this.status = status;
        this.errorMessage = message;
    }
}
