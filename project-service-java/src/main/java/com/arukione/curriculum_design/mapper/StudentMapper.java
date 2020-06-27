package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.VO.ApplicationStatusInfo;
import com.arukione.curriculum_design.model.VO.GuideStudentInfo;
import com.arukione.curriculum_design.model.VO.StudentApplication;
import com.arukione.curriculum_design.model.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface StudentMapper extends BaseMapper<Student> {

    @Update("update student set SID=#{value} where SID=#{sid}")
    int updateSID(String value,String sid);

    //获取除密码外的单个学生所有信息
    @Select("select SID,SName  name,Grade,ProfID professionId,ClassNumber from student where SID=#{sid}")
    Student getStudent(String SID);

    //获取指导学生信息
    @Select("select s.SName,s.SID,dpt.DeptName,pro.ProfName,s.ClassNumber,s.Grade,topic.TopicName " +
            "from topic_info topic,application app,student s,department dpt,profession pro " +
            "where topic.TID=#{tid} and topic.TopicID=app.TopicID  " +
            "and topic.SID=s.SID and app.Status='1' " +
            "and s.ProfID=pro.ProfID and pro.DeptID=dpt.DeptID")
    ArrayList<GuideStudentInfo> getGuideStudentInfo(String tid);

    //获取申请处理记录
    @Select("select topic.TopicName,student.SName,application.ApplyTime,application.`Status`,Source " +
            "from topic_info topic,application,student " +
            "where topic.TID=#{tid} and Status!='0'" +
            "and topic.TopicID=application.TopicID and student.SID=application.SID ")
    ArrayList<ApplicationStatusInfo> getApplicationStatusInfo(String tid);

    //获取未处理的学生申请
    @Select("select topic.TopicName,topic.Source,topic.TopicID,type.TypeName,topic.Introduction," +
            "student.SName,student.SID,profession.ProfName,student.ClassNumber," +
            "application.ApplyTime " +
            "from topic_info topic,topic_type type,application,student,profession " +
            "where topic.TID=#{tid} and topic.TypeID=type.TypeID " +
            "and topic.TopicID=application.TopicID and application.SID=student.SID " +
            "and student.ProfID=profession.ProfID " +
            "and Status='0'")
    ArrayList<StudentApplication> getStudentApplication(String tid);
}
