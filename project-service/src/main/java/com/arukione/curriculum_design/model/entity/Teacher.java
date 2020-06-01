package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teacher")
public class Teacher extends User {
    @TableId("TID")
    String tid;
    @TableField("TName")
    String name;
    @TableField("Position")
    String position;
    @TableField("Rank")
    String rank;
    @TableField("GuideProfID")
    String guideProfID;
    @TableField(exist = false)
    String guideProfession;
    @TableField("Phone")
    String phone;
    @TableField("Email")
    String email;
    @TableField("TopicDemand")
    String topicDemand;
    @TableField("Password")
    String password;
}
