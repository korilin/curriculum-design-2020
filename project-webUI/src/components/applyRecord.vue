<template>
  <List class="Padding20">
    <ListItem style="background:#f8f8f9">
      <Row class="row">
        <Col span="5">申请课题</Col>
        <Col span="5">申请类型</Col>
        <Col span="5">导师</Col>
        <Col span="5">申请时间</Col>
        <Col span="4">申请结果</Col>
      </Row>
    </ListItem>
    <ListItem v-for="(info,index) in applyRecord" :key="index">
      <Row class="row">
        <Col span="5">{{info.topicInfo.topicName}}</Col>
        <Col span="5">{{applyType[info.topicInfo.source]}}</Col>
        <Col span="5">{{info.teacherName}}</Col>
        <Col span="5">{{info.applyInfo.applyTime}}</Col>
        <Col span="4" :class="'status' + info.applyInfo.status">{{status[info.applyInfo.status]}}</Col>
      </Row>
    </ListItem>
  </List>
</template>

<script>
export default {
  name: "ApplyRecord",
  data() {
    return {
      applyType: {
        0: "导师课题",
        1: "学生课题"
      },
      status: {
        0: "申请中",
        1: "申请通过",
        2: "申请拒绝",
        3: "已取消申请",
        4: "系统取消"
      },
      applyRecord: []
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getApplyRecord",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        console.log(response.data);
        if (status == 200) this.applyRecord = response.data.applyRecord;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else if (status == 406) this.applyRecord = [];
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
.row {
  width: 100%;
  text-align: center;
}
.Padding20 {
  padding: 20px;
}
.status0 {
  color: #5cadff;
}
.status1 {
  color: #19be6b;
}
.status2 {
  color: #ed4014;
}
.status3 {
  color: #515a6e;
}
.status4 {
  color: #ff9900;
}
</style>