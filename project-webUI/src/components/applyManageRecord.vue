<template>
  <List class="Padding20">
    <ListItem style="background:#f8f8f9">
      <Row class="row">
        <Col span="5">申请课题</Col>
        <Col span="5">申请类型</Col>
        <Col span="5">申请人</Col>
        <Col span="5">申请时间</Col>
        <Col span="4">申请结果</Col>
      </Row>
    </ListItem>
    <ListItem v-for="(info,index) in applyRecord" :key="index">
      <Row class="row">
        <Col span="5">{{info.topicName}}</Col>
        <Col span="5">{{applyType[info.source]}}</Col>
        <Col span="5">{{info.sname}}</Col>
        <Col span="5">{{info.applyTime}}</Col>
        <Col span="4" :class="'status' + info.status">{{status[info.status]}}</Col>
      </Row>
    </ListItem>
  </List>
</template>

<script>
export default {
  name: "ApplyManageRecord",
  data() {
    return {
      applyType: {
        0: "导师课题",
        1: "学生课题"
      },
      status: {
          1:"已同意该申请",
          2:"已拒绝该申请",
          3:"申请已被学生取消",
          4:"申请已被系统取消"
      },
      applyRecord: []
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getApplicationStatus",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        console.log(response.data);
        if (status == 200) this.applyRecord = response.data.applicationStatusInfoList;
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
.status1 {
    color: #19be6b;
}
.status2 {
    color: #ed4014;
}
.status3 {
    color: #515a6e;
}
</style>