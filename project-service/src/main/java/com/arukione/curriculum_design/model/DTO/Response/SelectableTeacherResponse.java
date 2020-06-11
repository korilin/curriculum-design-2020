package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectableTeacherResponse extends Response {

    List<Teacher> teachers = new ArrayList<>();

    public SelectableTeacherResponse(int status, String message){
        super(status, message);
    }

    public SelectableTeacherResponse(int status, List<Teacher> teachers) {
        super(status);
        this.teachers = teachers;
    }
}
