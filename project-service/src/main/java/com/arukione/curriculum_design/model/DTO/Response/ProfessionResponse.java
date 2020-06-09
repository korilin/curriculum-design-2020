package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.Profession;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionResponse extends Response {

    HashMap<String, Profession> professions = new HashMap<>();

    public ProfessionResponse(int status, ArrayList<Profession> professions) {
        super(status);
        for (Profession profession : professions) {
            this.professions.put(profession.getId(),profession);
        }
    }
}
