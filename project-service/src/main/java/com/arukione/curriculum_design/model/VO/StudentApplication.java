package com.arukione.curriculum_design.model.VO;

import lombok.Data;

@Data
public class StudentApplication {
    String TopicName;
    String Source;
    String SName;
    String ApplyTime;

    //课题信息
    String TopicID;
    String TypeName;
    String Introduction;

    //学生信息
    String SID;
    String ProfName;
    String ClassNumber;
}
