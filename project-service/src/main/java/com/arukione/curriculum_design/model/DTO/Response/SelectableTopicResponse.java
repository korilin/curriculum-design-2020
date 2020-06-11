package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectableTopicResponse extends Response {

    ArrayList<SelectableTopicInfo> selectableTopicInfos = new ArrayList<>();

    public SelectableTopicResponse(int status, ArrayList<SelectableTopicInfo> selectableTopicInfos){
        super(status);
        this.selectableTopicInfos = selectableTopicInfos;
    }

    public SelectableTopicResponse(int status, String mes){
        super(status, mes);
    }
}
