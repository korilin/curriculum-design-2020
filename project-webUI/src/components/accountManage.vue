<template>
  <div class="wrap">
    <div class="ssw">
      <div class="selectionWrap">
        <Select
          v-if="tabstatus=='Student'"
          v-model="selectDept"
          clearable
          class="selection"
          placeholder="选择院系"
        >
          <Option v-for="value in departments" :value="value" :key="value">{{value}}</Option>
        </Select>
        <Select
          v-if="tabstatus=='Student'"
          v-model="selectProfession"
          clearable
          class="selection"
          placeholder="选择专业"
        >
          <template v-for="(value,key) in professions">
            <Option
              v-if="selectDept?(selectDept===value.deptName?true:false):true"
              :value="key"
              :key="key"
            >{{value.name}}</Option>
          </template>
        </Select>
        <Select
          v-if="tabstatus=='Teacher'"
          v-model="selectProfession"
          clearable
          class="selection"
          placeholder="指导专业"
        >
          <Option v-for="(value,key) in professions" :value="key" :key="key">{{value.name}}</Option>
        </Select>
        <Select
          v-if="tabstatus=='Teacher'"
          v-model="selectRank"
          clearable
          class="selection"
          placeholder="选择职称"
        >
          <Option value="教授">教授</Option>
          <Option value="副教授">副教授</Option>
          <Option value="讲师">讲师</Option>
          <Option value="助教">助教</Option>
          <Option value="无">无</Option>
        </Select>
      </div>
      <div class="searchWrap">
        <Input
          @on-search="searchStudent"
          placeholder="输入学号"
          v-if="tabstatus=='Student'"
          search
          enter-button
          class="search"
        ></Input>
        <Input
          @on-search="searchTeacher"
          placeholder="输入工号"
          v-if="tabstatus=='Teacher'"
          search
          enter-button
          class="search"
        ></Input>
      </div>
    </div>
    <Tabs size="small" v-model="tabstatus">
      <TabPane label="学生" name="Student">
        <List>
          <ListItem class="row-wrap" style="background:#f8f8f9">
            <Row class="row">
              <Col span="5">学号</Col>
              <Col span="3">姓名</Col>
              <Col span="3">年级</Col>
              <Col span="5">院系</Col>
              <Col span="5">专业</Col>
              <Col span="3">操作</Col>
            </Row>
          </ListItem>
          <template v-for="(student,index) in studentList">
            <template
              v-if="selectDept?((selectDept===professions[student.professionId].deptName)?true:false):true"
            >
              <template
                v-if="selectProfession?(selectProfession===student.professionId?true:false):true"
              >
                <ListItem
                  class="row-wrap"
                  :key="index"
                  v-if="selectSID?(student.sid==selectSID?true:false):true"
                >
                  <Row class="row">
                    <Col span="5">{{student.sid}}</Col>
                    <Col span="3">{{student.name}}</Col>
                    <Col span="3">{{student.grade}}</Col>
                    <Col span="5">{{professions[student.professionId].deptName}}</Col>
                    <Col span="5">{{professions[student.professionId].name}}</Col>
                    <Col span="3">
                      <Button
                        type="primary"
                        size="small"
                        style="margin-right:10px"
                        @click="changeStudent(index)"
                      >修改</Button>
                      <Button type="error" size="small" @click="deleteStudent(student.sid,index)">删除</Button>
                    </Col>
                  </Row>
                </ListItem>
              </template>
            </template>
          </template>
        </List>
      </TabPane>
      <TabPane label="导师" name="Teacher">
        <List>
          <ListItem class="row-wrap" style="background:#f8f8f9">
            <Row class="row">
              <Col span="5">工号</Col>
              <Col span="3">姓名</Col>
              <Col span="4">职位</Col>
              <Col span="4">职称</Col>
              <Col span="5">指导专业</Col>
              <Col span="3">操作</Col>
            </Row>
          </ListItem>
          <template v-for="(teacher,index) in teacherList">
            <template
              v-if="selectProfession?(selectProfession===teacher.guideProfID?true:false):true"
            >
              <template v-if="selectRank?(selectRank===teacher.trank?true:false):true">
                <ListItem
                  class="row-wrap"
                  :key="index"
                  v-if="selectTID?(teacher.tid==selectTID?true:false):true"
                >
                  <Row class="row">
                    <Col span="5">{{teacher.tid}}</Col>
                    <Col span="3">{{teacher.name}}</Col>
                    <Col span="4">{{teacher.position}}</Col>
                    <Col span="4">{{teacher.trank}}</Col>
                    <Col span="5">{{professions[teacher.guideProfID].name}}</Col>
                    <Col span="3">
                      <Button
                        type="primary"
                        size="small"
                        style="margin-right:10px"
                        @click="changeTeacher(index)"
                      >修改</Button>
                      <Button type="error" size="small" @click="deleteTeacher(teacher.tid,index)">删除</Button>
                    </Col>
                  </Row>
                </ListItem>
              </template>
            </template>
          </template>
        </List>
      </TabPane>
    </Tabs>
    <Modal
      v-model="showInfoModal"
      class-name="vertical-center-modal"
      @on-ok="changeOK"
      @on-cancel="changeCancel"
      :loading="true"
    >
      <div style="width:300px;margin:40px auto">
        <div v-if="tabstatus=='Student'">
          <h2 v-if="showInfoModal">
            修改学生账号信息
            <br />
            {{studentList[paneSelect].sid}}
          </h2>
          <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
            <template v-for="(value,key) in studentList[paneSelect]">
              <Option :value="key" :key="key" v-if="key!='userType'">{{keyList[key]}}</Option>
            </template>
          </Select>
          <Input
            v-model="newValue"
            placeholder="填写修改后的信息"
            class="Top30"
            v-if="selectKey!='professionId'&&selectKey!=''"
          />
          <Select
            v-model="newValue"
            placeholder="选择修改后的专业"
            class="Top30"
            v-if="selectKey=='professionId'"
          >
            <Option v-for="(value,key) in professions" :value="key" :key="key">{{value.name}}</Option>
          </Select>
        </div>
        <div v-if="tabstatus=='Teacher'">
          <h2 v-if="showInfoModal">
            修改导师账号信息
            <br />
            {{teacherList[paneSelect].tid}}
          </h2>
          <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
            <template v-for="(value,key) in teacherList[paneSelect]">
              <Option :value="key" :key="key" v-if="key!='userType'">{{keyList[key]}}</Option>
            </template>
          </Select>

          <Input
            v-model="newValue"
            placeholder="填写修改后的信息"
            class="Top30"
            v-if="selectKey!='guideProfID'&&selectKey!='trank'&&selectKey!='topicDemand'&&selectKey!=''"
          />
          <Input
            v-model="newValue"
            class="Top30"
            :autosize="{minRows: 4}"
            type="textarea"
            placeholder="填写修改后的自选课题要求"
            v-if="selectKey==='topicDemand'"
          ></Input>
          <Select
            class="Top30"
            v-if="selectKey=='guideProfID'"
            v-model="newValue"
            placeholder="选择修改后的指导专业"
          >
            <Option v-for="(value,key) in professions" :value="key" :key="key">{{value.name}}</Option>
          </Select>
          <Select class="Top30" v-if="selectKey=='trank'" v-model="newValue" placeholder="选择修改后的职称">
            <Option value="教授">教授</Option>
            <Option value="副教授">副教授</Option>
            <Option value="讲师">讲师</Option>
            <Option value="助教">助教</Option>
            <Option value="无">无</Option>
          </Select>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  name: "AccountManage",
  data() {
    return {
      showInfoModal: false,
      loading: true,
      tabstatus: "Student",
      paneSelect: 0,
      selectKey: "",
      newValue: "",
      selectDept: "",
      selectProfession: "",
      selectRank: "",
      selectSID: "",
      selectTID: "",
      studentList: [],
      teacherList: [],
      professions: [],
      departments: [
        "城建与环境学院",
        "金融与贸易学院",
        "计算机与信息学院",
        "文学与传媒学院",
        "艺术学院",
        "商学院",
        "法学院",
        "智能制造学院",
        "外国语学院",
        "创意设计学院",
        "创新创业学院",
        "体育教学部",
        "马克思主义学院"
      ],
      keyList: {
        sid: "学号",
        tid: "工号",
        name: "姓名",
        professionId: "专业",
        grade: "年级",
        classNumber: "班号",
        password: "密码",
        guideProfID: "指导专业",
        position: "职位",
        trank: "职称",
        phone: "联系电话",
        email: "联系邮箱",
        topicDemand: "课题要求"
      }
    };
  },
  created() {
    this.axios
      .get("/getProfessions")
      .then(response => {
        if (response.data.status == 200)
          this.professions = response.data.professions;
        else this.$Message.error("专业列表获取失败");
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
    this.axios({
      method: "get",
      url: "/getAccount",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        var data = response.data;
        if (status == 200) {
          this.studentList = data.students;
          this.teacherList = data.teachers;
        } else if (status == 401) {
          this.$Message.error(data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else {
          this.$Message.error(data.message);
        }
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
  },
  methods: {
    changeTeacher: function(index) {
      this.paneSelect = index;
      this.showInfoModal = true;
    },
    deleteTeacher: function(tid, index) {
      this.$Modal.confirm({
        title: "确认删除导师账号：" + tid,
        content: "删除账号将会删除与该账号相关的所有数据，是否确认删除？",
        loading: true,
        onOk: () => {
          this.axios({
            method: "delete",
            url: "/deleteTeacher",
            params: {
              accessToken: localStorage.getItem("access_token"),
              tid: tid
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.teacherList.splice(index, 1);
                this.$Modal.remove();
                this.$Message.success(tid + "账号删除成功");
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
    changeStudent: function(index) {
      this.paneSelect = index;
      this.showInfoModal = true;
    },
    deleteStudent: function(sid, index) {
      this.$Modal.confirm({
        title: "确认删除学生账号：" + sid,
        content: "删除账号将会删除与该账号相关的所有数据，是否确认删除？",
        loading: true,
        onOk: () => {
          this.axios({
            method: "delete",
            url: "/deleteStudent",
            params: {
              accessToken: localStorage.getItem("access_token"),
              sid: sid
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.studentList.splice(index, 1);
                this.$Modal.remove();
                this.$Message.success(sid + "账号删除成功");
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
      if (this.tabstatus === "Student") {
        this.newValue = this.studentList[this.paneSelect][key];
      } else if (this.tabstatus === "Teacher") {
        this.newValue = this.teacherList[this.paneSelect][key];
      }
    },
    changeOK: function() {
      if (this.selectKey === "") {
        this.changeCancel();
      } else if (this.tabstatus === "Student") {
        if (this.newValue === this.studentList[this.paneSelect][this.selectKey])
          this.changeCancel();
        else {
          var sid = this.studentList[this.paneSelect]["sid"];
          this.axios({
            method: "post",
            url: "/changeStudentInfo",
            params: {
              accessToken: localStorage.getItem("access_token"),
              sid: sid,
              key: this.selectKey,
              value: this.newValue
            }
          })
            .then(response => {
              var data = response.data;
              if (data.status == 204) {
                this.studentList[this.paneSelect][
                  this.selectKey
                ] = this.newValue;
                this.$Message.success("修改成功");
              } else if (status == 401) {
                this.$Message.error(response.data.message);
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
        }
      } else if (this.tabstatus === "Teacher") {
        if (this.newValue === this.teacherList[this.paneSelect][this.selectKey])
          this.changeCancel();
        else {
          var tid = this.teacherList[this.paneSelect]["tid"];
          this.axios({
            method: "post",
            url: "/changeTeacherInfo",
            params: {
              accessToken: localStorage.getItem("access_token"),
              tid: tid,
              key: this.selectKey,
              value: this.newValue
            }
          })
            .then(response => {
              var data = response.data;
              if (data.status == 204) {
                this.teacherList[this.paneSelect][
                  this.selectKey
                ] = this.newValue;
                this.$Message.success("修改成功");
              } else if (status == 401) {
                this.$Message.error(response.data.message);
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
        }
      }
    },
    changeCancel: function() {
      this.paneSelect = 0;
      this.selectKey = "";
      this.newValue = "";
      this.showInfoModal = false;
    },
    searchStudent: function(SID) {
      this.selectSID = SID;
    },
    searchTeacher: function(TID) {
      this.selectTID = TID;
    }
  }
};
</script>

<style scoped>
.row-wrap {
  margin: 2px;
}
.row {
  width: 100%;
  text-align: center;
}
.Top30 {
  margin-top: 30px;
}
h2 {
  text-align: center;
  margin-bottom: 30px;
}
.ssw {
  display: flex;
}
.selectionWrap,
.searchWrap {
  width: 540px;
}
.selection {
  width: 250px;
  margin-right: 20px;
}
.search {
  width: 300px;
  text-align: center;
}
</style>