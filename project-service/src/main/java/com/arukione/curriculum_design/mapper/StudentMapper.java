package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentMapper extends BaseMapper<Student> {

    @Update("update student set SID=#{value} where SID=#{sid}")
    int updateSID(String value,String sid);

}
