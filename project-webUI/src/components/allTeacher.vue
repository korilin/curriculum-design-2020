<template>
  <div class="wrap">
    <div span="8" v-for="teacher in teachers" :key="teacher.TName" class="cardWrap">
      <Card class="card">
        <p slot="title" class="bold" style="text-align:center;color:#2b85e4">
          <Icon type="md-person" size="20" style="margin-right:20px" />
          {{teacher.name}}
        </p>
        <Row class="row left">
          <Col span="4" class="bold">职位:</Col>
          <Col span="20">{{teacher.position}}</Col>
        </Row>
        <Row class="row left">
          <Col span="4" class="bold">职称:</Col>
          <Col span="20">{{teacher.trank}}</Col>
        </Row>
        <Row class="row left">
          <Col span="4" class="bold">电话:</Col>
          <Col span="20">{{teacher.phone}}</Col>
        </Row>
        <Row class="row left">
          <Col span="4" class="bold">邮箱:</Col>
          <Col span="20">{{teacher.email}}</Col>
        </Row>
        <Row class="row left">
          <Col span="4" class="bold">自选课题要求:</Col>
          <Col span="20">{{teacher.topicDemand}}</Col>
        </Row>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: "AllTeacher",
  data() {
    return {
      teachers: []
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getSelectableTeacher",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        if (status == 200) this.teachers = response.data.teachers;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else this.$Message.error(response.data.message);
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
  min-width: 300px;
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
.cardWrap {
  width: 100%;
  margin-bottom: 30px;
}
</style>