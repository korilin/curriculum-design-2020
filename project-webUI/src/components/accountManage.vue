<template>
  <div class="wrap">
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
          <ListItem class="row-wrap" v-for="(student,index) in studentList" :key="index">
            <Row class="row">
              <Col span="5">{{student.SID}}</Col>
              <Col span="3">{{student.SName}}</Col>
              <Col span="3">{{student.Grade}}</Col>
              <Col span="5">{{student.Department}}</Col>
              <Col span="5">{{student.Profession}}</Col>
              <Col span="3">
                <Button
                  type="primary"
                  size="small"
                  style="margin-right:10px"
                  @click="changeStudent(index)"
                >修改</Button>
                <Button type="error" size="small" @click="deleteStudent(student.SID)">删除</Button>
              </Col>
            </Row>
          </ListItem>
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
          <ListItem class="row-wrap" v-for="(teacher,index) in teacherList" :key="index">
            <Row class="row">
              <Col span="5">{{teacher.TID}}</Col>
              <Col span="3">{{teacher.TName}}</Col>
              <Col span="4">{{teacher.Position}}</Col>
              <Col span="4">{{teacher.Rank}}</Col>
              <Col span="5">{{teacher.GuideProfession}}</Col>
              <Col span="3">
                <Button
                  type="primary"
                  size="small"
                  style="margin-right:10px"
                  @click="changeTeacher(index)"
                >修改</Button>
                <Button type="error" size="small" @click="deleteTeacher(teacher.TID)">删除</Button>
              </Col>
            </Row>
          </ListItem>
        </List>
      </TabPane>
    </Tabs>
    <Modal
      v-model="showInfoModal"
      class-name="vertical-center-modal"
      @on-ok="changeOK"
      @on-cancel="changeCancel"
    >
      <div style="width:300px;margin:40px auto">
        <div v-if="tabstatus=='Student'">
          <h2>账号：{{studentList[paneSelect].SID}}</h2>
          <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
            <Option
              v-for="(value,key) in studentList[paneSelect]"
              :value="key"
              :key="key"
            >{{keyList[key]}}</Option>
          </Select>
          <Input v-model="newValue" placeholder="填写修改后的信息" class="Top30" />
        </div>
        <div v-if="tabstatus=='Teacher'">
          <h2>账号：{{teacherList[paneSelect].TID}}</h2>
          <Select v-model="selectKey" placeholder="选择需要修改的信息" @on-change="keyChange">
            <Option
              v-for="(value,key) in teacherList[paneSelect]"
              :value="key"
              :key="key"
            >{{keyList[key]}}</Option>
          </Select>

          <Input
            v-model="newValue"
            placeholder="填写修改后的信息"
            class="Top30"
          />
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
      tabstatus: "Student",
      paneSelect: 0,
      selectKey: "",
      newValue: "",
      studentList: [
        {
          SID: "201835020822",
          SName: "林洁彬",
          Department: "计算机与信息学院",
          Profession: "软件工程",
          Grade: 2018,
          ClassNumber: 8,
          Password: "123456"
        },
        {
          SID: "201835020822",
          SName: "林洁彬",
          Department: "计算机与信息学院",
          Profession: "软件工程",
          Grade: 2018,
          ClassNumber: 8,
          Password: "123456"
        }
      ],
      teacherList: [
        {
          TID: "10001",
          TName: "某老师",
          GuideProfession: "软件工程",
          Position: "无",
          Rank: "无",
          Password: ""
        }
      ],
      professions: ["软件工程", "计算机与信息科学"],
      keyList: {
        SID: "学号",
        SName: "姓名",
        TName: "姓名",
        Department: "院系",
        Profession: "专业",
        Grade: "年级",
        ClassNumber: "班号",
        Password: "密码",
        GuideProfession: "指导专业",
        Position: "职位",
        Rank: "职称"
      }
    };
  },
  methods: {
    changeTeacher: function(index) {
      this.paneSelect = index;
      this.showInfoModal = true;
    },
    deleteTeacher: function(TID) {
      if (confirm("确认删除导师账号：" + TID)) {
        this.$Message.success(TID + "账号删除成功");
      }
    },
    changeStudent: function(index) {
      this.paneSelect = index;
      this.showInfoModal = true;
    },
    deleteStudent: function(SID) {
      if (confirm("确认删除学生账号：" + SID)) {
        this.$Message.success(SID + "账号删除成功");
      }
    },
    keyChange: function(key) {
      if (this.tabstatus === "Student") {
        this.newValue = this.studentList[this.paneSelect][key];
      }else if(this.tabstatus==="Teacher"){
        this.newValue = this.teacherList[this.paneSelect][key];
      }
    },
    changeOK: function() {
      this.$Message.success("修改成功！");
      this.changeCancel();
    },
    changeCancel: function() {
      this.paneSelect = 0;
      this.selectKey = "";
      this.newValue = "";
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
</style>