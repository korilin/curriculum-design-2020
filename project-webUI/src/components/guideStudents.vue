<template>
  <div class="wrap">
    <Row class="row">
      <Col span="6" v-for="student in students" :key="student.SID" class="col">
        <Card class="card">
          <p slot="title" class="bold">{{student.SName}}</p>
          <Row class="row left">
            <Col span="8" class="bold">学号:</Col>
            <Col span="16">{{student.SID}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">院系:</Col>
            <Col span="16">{{student.Department}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">专业班级:</Col>
            <Col span="16">{{student.ProfessionAndClass}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">年级:</Col>
            <Col span="16">{{student.Grade}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">所选课题:</Col>
            <Col span="16">{{student.TopicName}}</Col>
          </Row>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  name: "GuideStudents",
  data() {
    return {
      students: []
    };
  },
  created() {
    this.students = [
      {
        SName: "林洁彬",
        SID: "201835020822",
        Department: "计算机与信息学院",
        ProfessionAndClass: "软件工程8班",
        Grade: 2018,
        TopicName: "毕业设计选题系统"
      },
      {
        SName: "林洁彬",
        SID: "201835020822",
        Department: "计算机与信息学院",
        ProfessionAndClass: "软件工程8班",
        Grade: 2018,
        TopicName: "毕业设计选题系统"
      },
      {
        SName: "林洁彬",
        SID: "201835020822",
        Department: "计算机与信息学院",
        ProfessionAndClass: "软件工程8班",
        Grade: 2018,
        TopicName: "毕业设计选题系统"
      },
      {
        SName: "林洁彬",
        SID: "201835020822",
        Department: "计算机与信息学院",
        ProfessionAndClass: "软件工程8班",
        Grade: 2018,
        TopicName: "毕业设计选题系统"
      },
      {
        SName: "林洁彬",
        SID: "201835020822",
        Department: "计算机与信息学院",
        ProfessionAndClass: "软件工程8班",
        Grade: 2018,
        TopicName: "毕业设计选题系统"
      }
    ];
  }
};
</script>

<style scoped>
.wrap {
  margin: 20px;
}
.row {
  text-align: center;
  width: 100%;
}
.card {
  width: 90%;
  margin: auto;
  min-width: 280px;
}
.left {
  text-align: left;
  margin-top: 10px;
}
.bold {
  font-weight: bold;
}
.col {
  margin: 10px auto;
}
</style>