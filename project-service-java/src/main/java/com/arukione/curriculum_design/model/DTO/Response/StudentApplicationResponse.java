package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.StudentApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentApplicationResponse extends Response{
    ArrayList<StudentApplication> studentApplicationList;

    public StudentApplicationResponse(int status, String mes){super(status, mes);}

    public StudentApplicationResponse(int status,ArrayList<StudentApplication> studentApplicationList){
        super(status);
        this.studentApplicationList=studentApplicationList;
    }
}
