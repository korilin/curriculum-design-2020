package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.Profession;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionResponse extends Response {

    HashMap<String, String> professions = new HashMap<>();

    ProfessionResponse(int status, String mes) {
        super(status, mes);
    }

    public ProfessionResponse(int status, ArrayList<Profession> professions) {
        super(status);
        for (Profession profession : professions) {
            this.professions.put(profession.getId(),profession.getName());
        }
    }
}
