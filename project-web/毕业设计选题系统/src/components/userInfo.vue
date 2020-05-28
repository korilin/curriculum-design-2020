<template>
  <div class="user_info">
    <template v-if="userType=='Student'">学生</template>

    <template v-if="userType=='Teacher'">
      <div class="info_wrap teacher_info">
        <h1>您好，导师！</h1>
        <Form ref="TeacherInfo" :model="TeacherInfo" :rules="TeacherRule" :label-width="100" label-position="left">
           <FormItem label="姓名">
             <Input v-model="TeacherInfo.TName" disabled></Input>
           </FormItem>
           <FormItem label="工号">
             <Input v-model="TeacherInfo.TID" disabled></Input>
           </FormItem>
           <FormItem label="职位">
             <Input v-model="TeacherInfo.Position" disabled></Input>
           </FormItem>
           <FormItem label="职称">
             <Input v-model="TeacherInfo.Rank" disabled></Input>
           </FormItem>
           <FormItem label="指导专业">
             <Input v-model="TeacherInfo.GuideProfession" disabled></Input>
           </FormItem>
           <FormItem label="联系电话">
             <Input v-model="TeacherInfo.Phone" type="tel"></Input>
           </FormItem>
           <FormItem label="联系邮箱">
             <Input v-model="TeacherInfo.Email" type="email"></Input>
           </FormItem>
           <FormItem label="自选课题要求">
             <Input v-model="TeacherInfo.TopicDemand" type="textarea"  :autosize="{minRows:5}"></Input>
           </FormItem>
           <FormItem>
             <Button type="success" style="margin-right:20px" @click="changeInfo">修改信息</Button>
             <Button type="primary" @click="changePassword">修改密码</Button>
           </FormItem>
        </Form>
      </div>
    </template>

    <template v-if="userType=='Admin'">
      <div class="info_wrap admin_info">
        <h1>您好，管理员！</h1>
        <Form
          ref="AdminInfo"
          :model="AdminInfo"
          :rules="AdminInfoRule"
          :label-width="100"
          label-position="left"
        >
          <FormItem label="管理员ID">
            <Input v-model="AdminInfo.AdminID" disabled />
          </FormItem>
          <FormItem label="工号">
            <Input v-model="AdminInfo.TID" disabled />
          </FormItem>
          <div class="admin_button">
            <Button type="primary" @click="changePassword">修改密码</Button>
          </div>
        </Form>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  props: {
    userType: String
  },
  data() {
    return {
      AdminInfo: {},
      TeacherInfo: {},
      StudentInfo: {},
      AdminInfoRule: {}
    };
  },
  created() {
    this.AdminInfo = {
      AdminID: "10001",
      TID: "10002405",
      Password: "123456"
    };
    this.TeacherInfo = {
      TName: "老师",
      TID: "1002401",
      Position: "院长",
      Rank: "教授",
      GuideProfession: "软件工程",
      Phone: "13200000001",
      Email: "1000001@qq.com",
      TopicDemand: "无要求"
    };
    this.StudentInfo = {};
  },
  methods: {
    changeInfo: function(){
      this.$Modal.info({
        content: this.TeacherInfo.Phone+"||"+this.TeacherInfo.Email+"||"+this.TeacherInfo.TopicDemand
      })
    },
    changePassword: function(){
      this.$Modal.error({
        content: "修改密码"
      })
    }
  }
};
</script>

<style scoped>
.info_wrap {
  text-align: center;
}
.admin_info {
  width: 300px;
  margin: auto;
}
h1 {
  margin: 30px auto 50px;
}
.teacher_info {
  width: 500px;
  margin: auto;
}
.admin_button {
  margin-top: 50px
}
</style>