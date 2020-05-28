<template>
  <div>
    <List class="Padding20">
      <ListItem style="background:#f8f8f9">
        <Row class="row">
          <Col span="5">申请课题</Col>
          <Col span="4">申请类型</Col>
          <Col span="4">申请人</Col>
          <Col span="5">申请时间</Col>
          <Col span="6">操作</Col>
        </Row>
      </ListItem>
      <ListItem v-for="(info,index) in applicationInfo" :key="index">
        <Row class="row">
          <Col span="5">{{info.topicInfo.TopicName}}</Col>
          <Col span="4">{{applyType[info.topicInfo.Source]}}</Col>
          <Col span="4">{{info.studentInfo.SName}}</Col>
          <Col span="5">{{info.ApplyTime}}</Col>
          <Col span="6">
            <Button type="info" size="small" class="opButton" @click="showInfo(index)">查看</Button>
            <Button
              type="success"
              size="small"
              class="opButton"
              @click="accept(info.studentInfo.SID, info.topicInfo.TopicID, info.studentInfo.SName, info.topicInfo.TopicName)"
            >同意</Button>
            <Button
              type="error"
              size="small"
              class="opButton"
              @click="refuse(info.studentInfo.SID, info.topicInfo.TopicID, info.studentInfo.SName, info.topicInfo.TopicName)"
            >拒绝</Button>
          </Col>
        </Row>
      </ListItem>
    </List>
    <Modal v-model="showInfoModal" class-name="vertical-center-modal" width="40%">
      <div class="Padding20">
        <Tabs type="card" v-model="tabStatus">
          <TabPane label="课题信息" name="0">
            <div class="Padding10">课题编号：{{applicationInfo[index].topicInfo.TopicID}}</div>
            <div class="Padding10">课题名称：{{applicationInfo[index].topicInfo.TopicName}}</div>
            <div class="Padding10">课题类型：{{applicationInfo[index].topicInfo.Type}}</div>
            <div class="Padding10">课题来源：{{applyType[applicationInfo[index].topicInfo.Source]}}</div>
            <div class="Padding10">
              课题介绍：
              <div class="Padding20">{{applicationInfo[index].topicInfo.Introduction}}</div>
            </div>
          </TabPane>
          <TabPane label="学生信息" name="1">
            <div class="Padding10">学号：{{applicationInfo[index].studentInfo.SID}}</div>
            <div class="Padding10">姓名：{{applicationInfo[index].studentInfo.SName}}</div>
            <div class="Padding10">班级：{{applicationInfo[index].studentInfo.ClassName}}</div>
          </TabPane>
        </Tabs>
      </div>
      <div slot="footer">
        <Button type="primary" long @click="closeInfo">确认</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  name: "ApplyManage",
  data() {
    return {
      showInfoModal: false,
      tabStatus: "0",
      index: 0,
      applyType: {
        0: "导师课题",
        1: "学生课题"
      },
      applicationInfo: []
    };
  },
  created() {
    this.applicationInfo = [
      {
        topicInfo: {
          TopicID: "10003",
          TopicName: "毕业设计选题系统",
          Type: "开发类",
          Introduction: "管理系统介绍",
          Source: 0
        },
        studentInfo: {
          SID: "201835020822",
          SName: "ARUKI",
          ClassName: "软件工程8班"
        },
        ApplyTime: "2020-5-25 1:05"
      }
    ];
  },
  methods: {
    showInfo: function(index) {
      this.index = index;
      this.showInfoModal = true;
    },
    closeInfo: function() {
      this.index = 0;
      this.tabStatus = "0";
      this.showInfoModal = false;
    },
    accept: function(SID, TopicID, SName, TopicName) {
      this.$Modal.confirm({
        title: "是否接受“" + SName + "”对课题”" + TopicName + "“的申请?"
      });
    },
    refuse: function(SID, TopicID, SName, TopicName) {
      this.$Modal.confirm({
        title: "是否拒绝“" + SName + "”对课题”" + TopicName + "“的申请?"
      });
    }
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
.opButton {
  margin: auto 5px;
  font-size: 12px;
}
.Padding10 {
  padding: 10px;
}
</style>