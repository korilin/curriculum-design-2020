package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Update("update teacher set TID=#{value} where TID=#{tid}")
    int updateTID(String value, String tid);


    //查询教师除密码外所有信息
    @Select("select TID,TName name,Position,TRank,GuideProfID,Phone,Email,TopicDemand from teacher where TID=#{tid}")
    Teacher getTeacher(String tid);

    //查询所有教师除密码外所有信息
    @Select("select TID,TName name,Position,TRank,GuideProfID,Phone,Email,TopicDemand from teacher")
    ArrayList<Teacher> getAllTeacher();
}
