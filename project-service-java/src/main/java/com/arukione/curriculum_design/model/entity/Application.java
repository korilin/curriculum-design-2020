package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("application")
@Data
public class Application {
    @TableField("SID")
    String sid;
    @TableField("TopicID")
    String topicId;
    @TableField("ApplyTime")
    String ApplyTime;
    @TableField("Status")
    String Status;
}
