package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;

@Data
public class Response {
    int requestStatus;
    String ErrorMessage;

    public Response(int status){
        this.requestStatus = status;
        this.ErrorMessage = "";
    }

    public Response(int status, String message){
        this.requestStatus = status;
        this.ErrorMessage = message;
    }
}
