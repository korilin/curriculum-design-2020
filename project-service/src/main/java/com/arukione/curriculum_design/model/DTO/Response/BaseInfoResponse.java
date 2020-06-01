package com.arukione.curriculum_design.model.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseInfoResponse extends Response {
    String userType;
    String name;

    public BaseInfoResponse(int status, String mes) {
        super(status, mes);
    }

    public BaseInfoResponse(int status, String userType, String name) {
        super(status);
        this.userType = userType;
        this.name = name;
    }
}
