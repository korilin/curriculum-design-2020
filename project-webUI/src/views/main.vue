<style scoped>
.content-fade-enter-active {
  transition: all 0.5s;
}
.content-fade-leave-active {
  transition: all 0.2s;
}
.content-fade-enter,
.content-fade-leave-to {
  transform: translate(10px);
  opacity: 0;
}
.sideBar-fade-enter-active {
  transition: all 0.8s;
}
.sideBar-fade-leave-active {
  transition: all 1s;
}
.sideBar-fade-enter,
.sideBar-fade-leave-to {
  transform: translate(-10px);
  opacity: 0;
}

.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-header-bar {
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}
.ivu-layout-header {
  text-align: right;
}
</style>
<template>
  <div class="layout">
    <transition mode="out-in" name="sideBar-fade" appear>
      <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
        <Menu
          theme="dark"
          width="auto"
          @on-select="getSelectValue"
          v-bind:active-name="showComponent"
        >
          <MenuItem name="添加学生账号" v-if="userType=='Admin'">
            <Icon type="md-person-add" />添加学生账号
          </MenuItem>
          <MenuItem name="添加导师账号" v-if="userType=='Admin'">
            <Icon type="md-person-add" />添加导师账号
          </MenuItem>
          <MenuItem name="导入学生账号" v-if="userType=='Admin'">
            <Icon type="md-cloud-upload" />导入学生账号
          </MenuItem>
          <MenuItem name="导入导师账号" v-if="userType=='Admin'">
            <Icon type="md-cloud-upload" />导入导师账号
          </MenuItem>
          <MenuItem name="账号信息管理" v-if="userType=='Admin'">
            <Icon type="ios-people" />账号信息管理
          </MenuItem>

          <Submenu name="我的课题" v-if="userType=='Teacher'">
            <template slot="title">
              <Icon type="md-menu" />我的课题
            </template>
            <MenuItem name="添加课题">
              <Icon type="ios-add-circle-outline" />添加课题
            </MenuItem>
            <MenuItem name="课题管理">
              <Icon type="ios-settings" />课题管理
            </MenuItem>
          </Submenu>

          <Submenu name="申请管理" v-if="userType=='Teacher'">
            <template slot="title">
              <Icon type="ios-paper-plane" />申请管理
            </template>
            <MenuItem name="学生申请处理" v-if="userType=='Teacher'">
              <Icon type="ios-aperture-outline" />学生申请处理
            </MenuItem>
            <MenuItem name="申请处理记录" v-if="userType=='Teacher'">
              <Icon type="ios-search-outline" />申请处理记录
            </MenuItem>
          </Submenu>

          <Submenu name="指导学生" v-if="userType=='Teacher'">
            <template slot="title">
              <Icon type="ios-people" />指导学生
            </template>
            <MenuItem name="指导学生信息" v-if="userType=='Teacher'">
              <Icon type="ios-paper-outline" />指导学生信息
            </MenuItem>
          </Submenu>

          <Submenu name="课题申请" v-if="userType=='Student'">
            <template slot="title">
              <Icon type="ios-paper-plane" />课题申请
            </template>
            <MenuItem name="导师课题申请" v-if="userType=='Student'">
              <Icon type="ios-send-outline" />导师课题申请
            </MenuItem>
            <MenuItem name="自选课题申请" v-if="userType=='Student'">
              <Icon type="ios-send" />自选课题申请
            </MenuItem>
            <MenuItem name="申请记录查询" v-if="userType=='Student'">
              <Icon type="ios-search-outline" />申请记录查询
            </MenuItem>
          </Submenu>

          <Submenu name="课题浏览" v-if="userType=='Student'">
            <template slot="title">
              <Icon type="ios-paper" />课题浏览
            </template>
            <MenuItem name="可选课题浏览" v-if="userType=='Student'">
              <Icon type="md-reorder" />可选课题浏览
            </MenuItem>
            <MenuItem name="已通过课题" v-if="userType=='Student'">
              <Icon type="md-checkmark-circle-outline" />已通过课题
            </MenuItem>
          </Submenu>

          <Submenu name="导师浏览" v-if="userType=='Student'">
            <template slot="title">
              <Icon type="md-school" />导师浏览
            </template>
            <MenuItem name="所有导师信息" v-if="userType=='Student'">
              <Icon type="md-reorder" />所有导师信息
            </MenuItem>
            <MenuItem name="我的导师信息" v-if="userType=='Student'">
              <Icon type="ios-person" />我的导师信息
            </MenuItem>
          </Submenu>
        </Menu>
      </Sider>
    </transition>
    <Layout :style="{marginLeft: '200px'}">
      <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
        <Dropdown @on-click="accountCenter">
          <a href="javascript:void(0)">
            <Icon type="md-ionitron" size="30" />
            {{userName}}
            <Icon type="ios-arrow-down"></Icon>
          </a>
          <DropdownMenu slot="list">
            <DropdownItem name="UserInfo">个人信息</DropdownItem>
            <DropdownItem style="color: #ed4014" name="logout">退出登陆</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </Header>
      <Content :style="{padding: '0 16px 16px'}">
        <Breadcrumb :style="{margin: '16px 0'}">
          <BreadcrumbItem>{{userType}}</BreadcrumbItem>
          <BreadcrumbItem>{{userName}}</BreadcrumbItem>
          <BreadcrumbItem>{{showComponent}}</BreadcrumbItem>
        </Breadcrumb>
        <Card style="min-width: 745px">
          <div style="min-height: 600px">
            <transition mode="out-in" name="content-fade" appear>
              <template v-if="showComponent=='UserInfo'">
                <UserInfo v-bind:userType="userType"></UserInfo>
              </template>

              <template v-if="showComponent=='添加学生账号'">
                <AddStudent></AddStudent>
              </template>

              <template v-if="showComponent=='添加导师账号'">
                <AddTeacher></AddTeacher>
              </template>

              <template v-if="showComponent=='导入学生账号'">
                <ImportStudent></ImportStudent>
              </template>

              <template v-if="showComponent=='导入导师账号'">
                <ImportTeacher></ImportTeacher>
              </template>

              <template v-if="showComponent=='账号信息管理'">
                <AccountManage></AccountManage>
              </template>

              <template v-if="showComponent=='添加课题'">
                <AddTopic></AddTopic>
              </template>

              <template v-if="showComponent=='课题管理'">
                <TopicManage></TopicManage>
              </template>

              <template v-if="showComponent=='学生申请处理'">
                <ApplyManage></ApplyManage>
              </template>

              <template v-if="showComponent=='申请处理记录'">
                <ApplyManageRecord></ApplyManageRecord>
              </template>

              <template v-if="showComponent=='指导学生信息'">
                <GuideStudents></GuideStudents>
              </template>

              <template v-if="showComponent=='导师课题申请'">
                <TeacherTopicApply></TeacherTopicApply>
              </template>

              <template v-if="showComponent=='自选课题申请'">
                <StudentTopicApply></StudentTopicApply>
              </template>

              <template v-if="showComponent=='申请记录查询'">
                <ApplyRecord></ApplyRecord>
              </template>

              <template v-if="showComponent=='可选课题浏览'">
                <AllTopic></AllTopic>
              </template>

              <template v-if="showComponent=='已通过课题'">
                <MyTopic></MyTopic>
              </template>

              <template v-if="showComponent=='所有导师信息'">
                <AllTeacher></AllTeacher>
              </template>

              <template v-if="showComponent=='我的导师信息'">
                <MyTeacher></MyTeacher>
              </template>
            </transition>
          </div>
        </Card>
      </Content>
    </Layout>
  </div>
</template>

<script>
import AddStudent from "../components/addStudent";
import AddTeacher from "../components/addTeacher";
import UserInfo from "../components/userInfo";
import AccountManage from "../components/accountManage";
import AddTopic from "../components/addTopic";
import TopicManage from "../components/topicManage";
import ApplyManage from "../components/applyManage";
import ApplyManageRecord from "../components/applyManageRecord";
import GuideStudents from "../components/guideStudents";
import TeacherTopicApply from "../components/teacherTopicApply";
import StudentTopicApply from "../components/studentTopicApply";
import AllTopic from "../components/allTopic";
import MyTopic from "../components/myTopic";
import ApplyRecord from "../components/applyRecord";
import AllTeacher from "../components/allTeacher";
import MyTeacher from "../components/myTeacher";
import ImportStudent from "../components/importStudent";
import ImportTeacher from "../components/importTeacher";

export default {
  name: "Main",
  props: {
    userType: String,
    userName: String
  },
  data() {
    return {
      showComponent: "UserInfo"
    };
  },
  components: {
    AddStudent,
    AddTeacher,
    UserInfo,
    AccountManage,
    AddTopic,
    TopicManage,
    ApplyManage,
    ApplyManageRecord,
    GuideStudents,
    TeacherTopicApply,
    StudentTopicApply,
    AllTopic,
    MyTopic,
    ApplyRecord,
    AllTeacher,
    MyTeacher,
    ImportStudent,
    ImportTeacher
  },
  methods: {
    getSelectValue: function(name) {
      this.showComponent = name;
    },
    accountCenter: function(name) {
      if (name == "UserInfo") this.showComponent = name;
      else if (name == "logout") {
        this.axios
          .get("/logout?accessToken=" + localStorage.getItem("access_token"))
          .then(response => {
            if (response.data.status == 204) {
              localStorage.removeItem("access_token");
              this.$Message.info("退出成功");
              this.$router.replace({
                name: "Login"
              });
            }
          })
          .catch(error => {
            this.$Message.error(error.message);
          });
      }
    }
  }
};
</script>