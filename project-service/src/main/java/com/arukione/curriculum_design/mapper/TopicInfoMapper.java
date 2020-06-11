package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicInfoMapper extends BaseMapper<Topic> {

    @Select("select * from topic_info where TopicID=#{topicid}")
    Topic getTopic(String topicid);
}
