package com.arukione.curriculum_design.model.DTO.Response;

import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import com.arukione.curriculum_design.model.entity.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectableTopicResponse extends Response {

    List<SelectableTopicInfo> selectableTopicInfos = new ArrayList<>();

    public SelectableTopicResponse(int status, List<SelectableTopicInfo> selectableTopicInfos){
        super(status);
        this.selectableTopicInfos = selectableTopicInfos;
    }

    public SelectableTopicResponse(int status, String mes){
        super(status, mes);
    }

}
