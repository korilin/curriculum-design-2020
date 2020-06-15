package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.VO.SelectableTopicInfo;
import com.arukione.curriculum_design.model.VO.TopicView;
import com.arukione.curriculum_design.model.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TopicInfoMapper extends BaseMapper<Topic> {


    @Select("select * from topic_info where TopicID=#{topicId}")
    Topic getTopic(String topicId);

    @Select("select topic_info.TopicID,TopicName,Introduction,TypeName as Type,SID, Source " +
            "from topic_info,topic_type " +
            "where " +
            "topic_info.TypeID=topic_type.TypeID and " +
            "TID=#{tid}")
    ArrayList<TopicView> getTopicN(String tid);

    @Select("select topicId, topicName, teacher.TID, TName, TypeName, Introduction " +
            "from topic_info,teacher,topic_type where " +
            "topic_info.TID=teacher.TID and " +
            "topic_info.TypeID=topic_type.TypeID and " +
            "GuideProfID=#{profId} and SID is null and Source='0'")
    ArrayList<SelectableTopicInfo> getSelectableTopicInfo(String profId);

    @Update("update topic_info set SID=null where SID=#{sid}")
    void updateSID(String sid);
}
