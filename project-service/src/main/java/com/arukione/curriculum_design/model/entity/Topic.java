package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("topic_info")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @TableId(value = "TopicID")
    String topicId;
    @TableField("TopicName")
    String topicName;
    @TableField("Introduction")
    String introduction;
    @TableField("TID")
    String tid;
    @TableField("TypeID")
    String typeId;
    @TableField("Source")
    String source;
    @TableField("SID")
    String sid;
}
