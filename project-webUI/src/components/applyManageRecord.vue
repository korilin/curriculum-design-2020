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
        <Col span="5">{{info.TopicName}}</Col>
        <Col span="5">{{applyType[info.TopicSource]}}</Col>
        <Col span="5">{{info.SName}}</Col>
        <Col span="5">{{info.ApplyTime}}</Col>
        <Col span="4" :class="'status' + info.Status">{{status[info.Status]}}</Col>
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
    this.applyRecord = [
      {
        TopicName: "毕业设计选题系统",
        TopicSource: 0,
        SName: "ARUKI",
        ApplyTime: "2020-5-25 1:05",
        Status: 1
      },
      {
        TopicName: "毕业设计选题系统",
        TopicSource: 1,
        SName: "ARUKI",
        ApplyTime: "2020-5-25 1:05",
        Status: 2
      },
      {
        TopicName: "毕业设计选题系统",
        TopicSource: 0,
        SName: "ARUKI",
        ApplyTime: "2020-5-25 1:05",
        Status: 3
      }
    ];
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