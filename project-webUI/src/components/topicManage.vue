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
          <Col span="5">{{topic.topicID}}</Col>
          <Col span="5">{{topic.topicName}}</Col>
          <Col span="5">{{topic.type}}</Col>
          <Col span="5">{{topic.sname?topic.sname:"未选"}}</Col>
          <Col span="4">
            <Button type="primary" size="small" class="opButton" @click="changeTopic(index)">修改</Button>
            <Button
              type="error"
              size="small"
              class="opButton"
              @click="deleteTopic(topic.topicID,index)"
            >删除</Button>
          </Col>
        </Row>
      </ListItem>
    </List>
    <Modal
      v-model="showInfoModal"
      :title="'修改课题信息：'+topicList[selectTopic].topicID"
      class-name="vertical-center-modal"
      @on-ok="changeOK"
      @on-cancel="changeCancel"
      :loading="true"
    >
      <div style="width:400px;margin:30px auto">
        <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
          <template v-for="(value,key) in topicList[selectTopic]">
            <Option
              v-if="key=='topicName'||key=='introduction'||key=='type'"
              :value="key"
              :key="key"
            >{{keyList[key]}}</Option>
          </template>
        </Select>
        <Input
          v-model="newValue"
          placeholder="填写修改后的信息"
          class="Top30"
          v-if="selectKey!='introduction'"
        ></Input>
        <Input
          v-model="newValue"
          class="Top30"
          :autosize="{minRows: 4}"
          type="textarea"
          placeholder="填写修改后的信息"
          v-if="selectKey==='introduction'"
        ></Input>
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
        topicName: "课题名",
        introduction: "课题介绍",
        type: "课题类型"
      },
      topicList: []
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getTeacherTopic",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        if (response.data.status == 200) this.topicList = response.data.topicTS;
        else this.$Message.error("课题获取失败");
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
  },
  methods: {
    changeTopic: function(index) {
      this.selectTopic = index;
      this.showInfoModal = true;
    },
    deleteTopic: function(topicID, index) {
      this.$Modal.confirm({
        title: "确认删除课题：" + topicID,
        loading: true,
        onOk: () => {
          this.axios({
            method: "delete",
            url: "/deleteTopic",
            params: {
              accessToken: localStorage.getItem("access_token"),
              topicId: topicID
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.topicList.splice(index, 1);
                this.$Modal.remove();
                this.$Message.success(topicID + "账号删除成功");
              } else if (status == 401) {
                this.$Message.error(response.data.message);
                localStorage.removeItem("access_token");
                this.$router.replace({
                  name: "Login"
                });
              } else this.$Message.error(data.message);
            })
            .catch(error => {
              this.$Message.error(error.message);
              console.log(error);
            });
        }
      });
    },
    keyChange: function(key) {
      this.newValue = this.topicList[this.selectTopic][key];
    },
    changeOK: function() {
      var id = this.topicList[this.selectTopic].topicID;
      this.axios({
        method: "post",
        url: "/changeTopicInfo",
        params: {
          accessToken: localStorage.getItem("access_token"),
          key: this.selectKey,
          value: this.newValue,
          topicID: id
        }
      })
        .then(response => {
          var data = response.data;
          if (data.status == 204) {
            this.studentList[this.paneSelect][this.selectKey] = this.newValue;
            this.$Message.success("修改成功");
          } else if (status == 401) {
            this.$Message.error(data.message);
            localStorage.removeItem("access_token");
            this.$router.replace({
              name: "Login"
            });
          } else {
            this.$Message.error(data.message);
          }
          this.changeCancel();
        })
        .catch(error => {
          this.$Message.error(error.message);
          console.log(error);
          this.changeCancel();
        });
    },
    changeCancel: function() {
      this.selectTopic = 0;
      this.selectKey = "";
      this.newValue = "";
      this.showInfoModal = false;
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