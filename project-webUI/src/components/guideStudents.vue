<template>
  <div class="wrap">
    <Row class="row">
      <Col span="6" v-for="student in students" :key="student.sid" class="col">
        <Card class="card">
          <p slot="title" class="bold">{{student.sname}}</p>
          <Row class="row left">
            <Col span="8" class="bold">学号:</Col>
            <Col span="16">{{student.sid}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">院系:</Col>
            <Col span="16">{{student.deptName}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">专业班级:</Col>
            <Col span="16">{{student.profName+student.classNumber}}班</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">年级:</Col>
            <Col span="16">{{student.grade}}</Col>
          </Row>
          <Row class="row left">
            <Col span="8" class="bold">所选课题:</Col>
            <Col span="16">{{student.topicName}}</Col>
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
    this.axios({
      method: "get",
      url: "/getGuideStudentInfo",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        console.log(response.data);
        if (status == 200) this.students = response.data.guideStudentInfoList;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else if (status == 406) this.students = [];
        else this.$Message.error(response.data.message);
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
  
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