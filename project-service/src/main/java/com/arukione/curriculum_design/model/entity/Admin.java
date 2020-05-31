package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class Admin {

    @TableId("AdminID")
    String adminId;
    @TableField("Password")
    String password;
    @TableField("TID")
    String tid;
}
