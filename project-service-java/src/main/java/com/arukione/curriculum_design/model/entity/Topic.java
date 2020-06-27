package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.KeyException;

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

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "topicName":
                this.topicName = value;
                break;
            case "introduction":
                this.introduction = value;
                break;
            case "type":
                this.typeId = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
