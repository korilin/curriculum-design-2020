package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.entity.TopicType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class TypeResponse extends Response{

    HashMap<String ,String> type = new HashMap<>();

    public TypeResponse(int status, ArrayList<TopicType> topicTypes) {
        super(status);
        for (TopicType topicType: topicTypes) {
            this.type.put(topicType.getTypeId(),topicType.getTypeName());
        }
    }

}
