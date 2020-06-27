package com.arukione.curriculum_design.model.DTO.Request;

import com.arukione.curriculum_design.model.entity.Teacher;
import lombok.Data;

@Data
public class NewTeacher {
    String tid;
    String name;
    String position;
    String rank;
    String guideProfId;
    String phone;
    String email;
    String password;
    String accessToken;

    public Teacher convertTeacher(){
        position = position.equals("") ?"无":position;
        rank = rank.equals("") ?"无":rank;
        phone = phone.equals("") ?"无":phone;
        email = email.equals("") ?"无":email;
        return Teacher.builder()
                .tid(tid)
                .name(name)
                .position(position)
                .trank(rank)
                .guideProfID(guideProfId)
                .phone(phone)
                .email(email)
                .password(password)
                .build();
    }
}
