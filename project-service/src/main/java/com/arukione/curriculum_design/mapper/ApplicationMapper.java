package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ApplicationMapper extends BaseMapper<Application> {

    //通过处理状态获取application
    @Select("select * from application where Status=#{status}")
    ArrayList<Application> getApplicationsOfStatus(String status);

    //通过学生id获取application
    @Select("select * from application where SID=#{sid}")
    ArrayList<Application> getApplicationsOfSID(String sid);

    //通过学生id获取通过与否的application
    @Select("select * from application where SID=#{sid} and Status=#{status}")
    ArrayList<Application> getApplicationsOfSIDAndStatus(String sid,String status);
}
