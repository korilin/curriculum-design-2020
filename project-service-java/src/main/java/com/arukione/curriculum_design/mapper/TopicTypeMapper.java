package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.TopicType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicTypeMapper extends BaseMapper<TopicType> {

    @Select("select * from topic_type where TypeID=#{typeId}")
    TopicType getTopicType(String typeId);
}
