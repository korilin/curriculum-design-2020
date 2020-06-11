package com.arukione.curriculum_design.mapper;

import com.arukione.curriculum_design.model.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("select * from application where Status=#{status}")
    ArrayList<Application> getApplicationsOfStatus(String status);
}
