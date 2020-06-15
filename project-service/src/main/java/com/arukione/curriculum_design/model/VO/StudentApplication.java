package com.arukione.curriculum_design.model.VO;

import lombok.Data;

@Data
public class StudentApplication {
    String ApplyTime;

    //课题信息
    String TopicID;
    String TypeName;
    String Introduction;
    String TopicName;
    String Source;

    //学生信息
    String SID;
    String SName;
    String ProfName;
    String ClassNumber;
}
