package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.TO.TopicT;
import com.arukione.curriculum_design.model.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TopicInfoMapper extends BaseMapper<Topic> {

    @Select("select topic_info.TopicID,TopicName,Introduction,TypeName as Type,SID " +
            "from topic_info,topic_type " +
            "where " +
            "topic_info.TypeID=topic_type.TypeID and " +
            "TID=#{tid}")
    ArrayList<TopicT> getTopicN(String tid);
}
