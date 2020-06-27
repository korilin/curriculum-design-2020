package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Profession;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionMapper extends BaseMapper<Profession> {

    @Select("select ProfID as id," +
            "ProfName as name," +
            "profession.DeptID as deptId," +
            "DeptName as deptName " +
            "from profession,department " +
            "where " +
            "profession.DeptID=department.DeptID;")
    List<Profession> getProfessionsIncludeDepartment();

    @Select("select ProfName name,DeptID from profession where ProfID=#{profid}")
    Profession getProfession(String profid);
}
