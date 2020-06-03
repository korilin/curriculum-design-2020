package com.arukione.curriculum_design.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class User  implements Serializable {
    @TableField(exist = false)
    String userType;
    @TableField(exist = false)
    String name;
}
