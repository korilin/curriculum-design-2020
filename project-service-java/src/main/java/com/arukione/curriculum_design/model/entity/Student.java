package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.security.KeyException;

@TableName("student")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @TableId("SID")
    String sid;
    @TableField("SName")
    String name;
    @TableField("Grade")
    Integer grade;
    @TableField("ProfID")
    String professionId;
    @TableField("ClassNumber")
    Integer classNumber;
    @TableField("Password")
    String password;

    public void setValue(String key, String value) throws KeyException {
        switch (key) {
            case "sid":
                this.sid = value;
                break;
            case "name":
                this.name = value;
                break;
            case "professionId":
                this.professionId = value;
                break;
            case "grade":
                this.grade = Integer.parseInt(value);
                break;
            case "classNumber":
                this.classNumber = Integer.parseInt(value);
                break;
            case "password":
                this.password = value;
                break;
            default:
                throw new KeyException("找不到该键对应的字段");
        }
    }
}
