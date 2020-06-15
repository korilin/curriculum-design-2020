package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.GuideStudentInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class GuideStudentInfoResponse extends Response{
    ArrayList<GuideStudentInfo> guideStudentInfoList;

    public GuideStudentInfoResponse(int status,ArrayList<GuideStudentInfo> guideStudentInfoList){
        super(status);
        this.guideStudentInfoList=guideStudentInfoList;
    }

}
