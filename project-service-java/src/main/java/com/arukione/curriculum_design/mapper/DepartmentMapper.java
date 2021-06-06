package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    //获取学院名称
    @Select("select DeptName name from department where DeptID=#{deptId}")
    Department getDepartment(String deptId);
}
