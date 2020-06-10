package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.TO.TopicT;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopicTResponse extends Response{

    ArrayList<TopicT> topicTS;

    public TopicTResponse(int status, ArrayList<TopicT> topicTS){
        super(status);
        this.topicTS = topicTS;
    }
}
