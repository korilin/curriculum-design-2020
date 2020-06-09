package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("topic_type")
@Data
public class TopicType {
    @TableId("TypeID")
    String typeId;
    @TableField("TypeName")
    String typeName;
}
