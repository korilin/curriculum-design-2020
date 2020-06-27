package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.Student;
import com.arukione.curriculum_design.model.entity.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountResponse extends Response{

    List<Student> students;
    List<Teacher> teachers;

    public AccountResponse(int status, String mes){
        super(status, mes);
    }

    public AccountResponse(int status, List<Student> students, List<Teacher> teachers){
        super(status);
        this.students = students;
        this.teachers = teachers;
    }
}
