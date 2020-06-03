package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("student")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User{

    @TableId("SID")
    String sid;
    @TableField("SName")
    String name;
    @TableField("Grade")
    Integer grade;
    @TableField("ProfID")
    String professionId;
    @TableField(exist = false)
    String profession;
    @TableField(exist = false)
    String department;
    @TableField("ClassNumber")
    Integer classNumber;
    @TableField("Password")
    String password;
}
