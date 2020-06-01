package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profession")
public class Profession {
    @TableId("ProfID")
    String id;
    @TableField("ProfName")
    String name;
    @TableField("DeptID")
    String deptId;
    @TableField(exist = false)
    String deptName;
}
