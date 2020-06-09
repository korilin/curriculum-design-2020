package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.security.KeyException;

@TableName("teacher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    @TableId("TID")
    String tid;
    @TableField("TName")
    String name;
    @TableField("Position")
    String position;
    @TableField("TRank")
    String trank;
    @TableField("GuideProfID")
    String guideProfID;
    @TableField("Phone")
    String phone;
    @TableField("Email")
    String email;
    @TableField("TopicDemand")
    String topicDemand;
    @TableField("Password")
    String password;

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "tid":
                this.tid = value;
                break;
            case "guideProfID":
                this.guideProfID = value;
                break;
            case "position":
                this.position = value;
                break;
            case "trank":
                this.trank = value;
                break;
            case "phone":
                this.phone = value;
                break;
            case "email":
                this.email = value;
                break;
            case "topicDemand":
                this.topicDemand = value;
                break;
            case "password":
                this.password = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
