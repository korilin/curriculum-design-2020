package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Update("update teacher set TID=#{value} where TID=#{tid}")
    int updateTID(String value, String tid);
}
