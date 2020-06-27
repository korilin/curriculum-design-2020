package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.ApplicationStatusInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationStatusInfoResponse extends Response {
    ArrayList<ApplicationStatusInfo> applicationStatusInfoList;

    public ApplicationStatusInfoResponse(int status, String mes){super(status, mes);}

    public ApplicationStatusInfoResponse(int status, ArrayList<ApplicationStatusInfo> applicationStatusInfoList){
        super(status);
        this.applicationStatusInfoList=applicationStatusInfoList;
    }
}
