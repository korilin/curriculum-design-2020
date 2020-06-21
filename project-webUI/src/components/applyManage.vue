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
          <Col span="5">{{info.topicName}}</Col>
          <Col span="4">{{applyType[info.source]}}</Col>
          <Col span="4">{{info.sname}}</Col>
          <Col span="5">{{info.applyTime}}</Col>
          <Col span="6">
            <Button type="info" size="small" class="opButton" @click="showInfo(index)">查看</Button>
            <Button
              type="success"
              size="small"
              class="opButton"
              @click="accept(info.sid, info.topicID, info.sname, info.topicName)"
            >同意</Button>
            <Button
              type="error"
              size="small"
              class="opButton"
              @click="refuse(info.sid, info.topicID, info.sname, info.topicName, index)"
            >拒绝</Button>
          </Col>
        </Row>
      </ListItem>
    </List>
    <Modal v-model="showInfoModal" class-name="vertical-center-modal" width="40%">
      <div class="Padding20">
        <Tabs type="card" v-model="tabStatus" v-if="showInfoModal">
          <TabPane label="课题信息" name="0">
            <div class="Padding10">课题编号：{{applicationInfo[index].topicID}}</div>
            <div class="Padding10">课题名称：{{applicationInfo[index].topicName}}</div>
            <div class="Padding10">课题类型：{{applicationInfo[index].typeName}}</div>
            <div class="Padding10">课题来源：{{applyType[applicationInfo[index].source]}}</div>
            <div class="Padding10">
              课题介绍：
              <div class="Padding20" style="white-space: pre-line">{{applicationInfo[index].introduction}}</div>
            </div>
          </TabPane>
          <TabPane label="学生信息" name="1">
            <div class="Padding10">学号：{{applicationInfo[index].sid}}</div>
            <div class="Padding10">姓名：{{applicationInfo[index].sname}}</div>
            <div
              class="Padding10"
            >班级：{{applicationInfo[index].profName + applicationInfo[index].classNumber}}班</div>
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
    this.axios({
      method: "get",
      url: "/getStudentApply",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        console.log(response.data);
        if (status == 200)
          this.applicationInfo = response.data.studentApplicationList;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else if (status == 406) this.applicationInfo = [];
        else this.$Message.error(response.data.message);
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
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
        title: "是否接受“" + SName + "”对课题”" + TopicName + "“的申请?",
        onOk: () => {
          this.axios({
            method: "post",
            url: "/applyManage",
            params: {
              accessToken: localStorage.getItem("access_token")
            },
            data: {
              sid: SID,
              topicId: TopicID,
              status: "1"
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.axios({
                  method: "get",
                  url: "/getStudentApply",
                  params: {
                    accessToken: localStorage.getItem("access_token")
                  }
                })
                  .then(response => {
                    var status = response.data.status;
                    console.log(response.data);
                    if (status == 200)
                      this.applicationInfo =
                        response.data.studentApplicationList;
                    else if (status == 401) {
                      this.$Message.error(response.data.message);
                      localStorage.removeItem("access_token");
                      this.$router.replace({
                        name: "Login"
                      });
                    } else if (status == 406) this.applicationInfo = [];
                    else this.$Message.error(response.data.message);
                  })
                  .catch(error => {
                    this.$Message.error(error.message);
                    console.log(error);
                  });
                this.$Message.success("已同意");
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
    refuse: function(SID, TopicID, SName, TopicName, index) {
      this.$Modal.confirm({
        title: "是否拒绝“" + SName + "”对课题”" + TopicName + "“的申请?",
        onOk: () => {
          this.axios({
            method: "post",
            url: "/applyManage",
            params: {
              accessToken: localStorage.getItem("access_token")
            },
            data: {
              sid: SID,
              topicId: TopicID,
              status: "2"
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.applicationInfo.splice(index, 1);
                this.$Message.success("已拒绝");
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