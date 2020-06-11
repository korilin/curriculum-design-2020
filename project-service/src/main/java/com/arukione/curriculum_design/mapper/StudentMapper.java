package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentMapper extends BaseMapper<Student> {

    @Update("update student set SID=#{value} where SID=#{sid}")
    int updateSID(String value,String sid);

    //获取除密码外的单个学生所有信息
    @Select("select SID,SName  name,Grade,ProfID professionId,ClassNumber from student where SID=#{sid}")
    Student getStudent(String SID);

}
