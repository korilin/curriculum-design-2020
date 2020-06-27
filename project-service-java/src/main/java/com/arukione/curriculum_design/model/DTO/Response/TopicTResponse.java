package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.TopicView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopicTResponse extends Response {

    ArrayList<TopicView> topicViews;
    int tNum;
    int sNum;

    public TopicTResponse(int status, ArrayList<TopicView> topicViews, int tNum, int sNum) {
        super(status);
        this.topicViews = topicViews;
        this.tNum = tNum;
        this.sNum = sNum;
    }
}
