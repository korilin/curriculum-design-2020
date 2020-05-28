<template>
  <div>
    <List>
      <ListItem class="Padding10">已有课题：{{topicList.length}}/20</ListItem>
      <ListItem style="background:#f8f8f9">
        <Row class="row">
          <Col span="5">课题编号</Col>
          <Col span="5">课题名</Col>
          <Col span="5">课题类型</Col>
          <Col span="5">已选学生</Col>
          <Col span="4">操作</Col>
        </Row>
      </ListItem>
      <ListItem v-for="(topic,index) in topicList" :key="index">
        <Row class="row">
          <Col span="5">{{topic.TopicID}}</Col>
          <Col span="5">{{topic.TopicName}}</Col>
          <Col span="5">{{topic.Type}}</Col>
          <Col span="5">{{topic.SName?topic.SName:"未选"}}</Col>
          <Col span="4">
            <Button type="primary" size="small" class="opButton" @click="changeTopic(index)">修改</Button>
            <Button
              type="error"
              size="small"
              class="opButton"
              @click="deleteTopic(topic.TopicID)"
            >删除</Button>
          </Col>
        </Row>
      </ListItem>
    </List>
    <Modal
      v-model="showInfoModal"
      :title="'课题：'+topicList[selectTopic].TopicID"
      class-name="vertical-center-modal"
      @on-ok="changeOK"
      @on-cancel="changeCancel"
    >
      <div style="width:400px;margin:30px auto">
        <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
          <Option
            v-for="(value,key) in topicList[selectTopic]"
            :value="key"
            :key="key"
          >{{keyList[key]}}</Option>
        </Select>
        <Input v-model="newValue" placeholder="填写修改后的信息" class="Top30"></Input>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  name: "TopicManage",
  data() {
    return {
      selectTopic: 0,
      selectKey: "",
      showInfoModal: false,
      newValue: "",
      keyList: {
        TopicName: "课题名",
        Introduction: "课题介绍",
        Type: "课题类型"
      },
      topicList: []
    };
  },
  created() {
    this.topicList = [
      {
        TopicID: 1003,
        TopicName: "课设1",
        Introduction: "无介绍",
        Type: "其它",
        SName: null
      },
      {
        TopicID: 1004,
        TopicName: "毕业设计选题系统",
        Introduction: "毕业设计选题系统课题介绍",
        Type: "软件设计类",
        SName: "ARUKI"
      }
    ];
  },
  methods: {
    changeTopic: function(index) {
      this.selectTopic = index;
      this.showInfoModal = true;
    },
    deleteTopic: function(TopicID) {
      if (this.$Modal.confirm({ title: "确认删除课题：" + TopicID })) {
        this.$Message.success("删除成功");
      }
    },
    keyChange: function(key) {
      this.newValue = this.topicList[this.selectTopic][key];
    },
    changeOK: function() {
      this.$Message.success("修改成功！");
      this.changeCancel();
    },
    changeCancel: function() {
      this.selectTopic = 0;
      this.selectKey = "";
      this.newValue = "";
    }
  }
};
</script>

<style scoped>
.Padding10 {
  padding-left: 15px;
  padding-right: 15px;
}
.row {
  width: 100%;
  text-align: center;
}
.opButton {
  font-size: 12px;
  margin: auto 5px;
}
.Top30 {
  margin-top: 30px;
}
</style>