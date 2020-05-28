<template>
  <div class="layout">
    <Header>
      <h1 class="login-title">毕业设计选题系统</h1>
    </Header>
    <Content class="login-content">
      <Menu mode="horizontal" active-name="student" @on-select="getSelectValue">
        <MenuItem name="student">
          <Icon type="ios-people" />学生
        </MenuItem>

        <MenuItem name="teacher">
          <Icon type="md-people" />导师
        </MenuItem>

        <MenuItem name="admin">
          <Icon type="ios-person" />管理员
        </MenuItem>
      </Menu>
      <Form
        :model="formData"
        label-position="left"
        :label-width="100"
        class="form"
        :rules="loginRule"
        ref="loginForm"
      >
        <FormItem label="学号" v-if="userType=='student'" prop="userid">
          <Input v-model="formData.userid" placeholder="请输入学号" />
        </FormItem>
        <FormItem label="工号" v-if="userType=='teacher'" prop="userid">
          <Input v-model="formData.userid" placeholder="请输入工号" />
        </FormItem>
        <FormItem label="管理员账号" v-if="userType=='admin'" prop="userid">
          <Input v-model="formData.userid" placeholder="请输入管理员账号" />
        </FormItem>
        <FormItem label="密码" prop="password">
          <Input v-model="formData.password" placeholder="请输入密码" type="password" />
        </FormItem>
        <Button type="primary" @click="submit('loginForm')" long style="margin-top:30px">登陆</Button>
      </Form>
    </Content>
    <Footer class="footer">
      <span>学校：东莞理工学院城市学院</span>
      <span>班级：2018级软件工程8班</span>
      <span>小组：林洁彬、谢铭轩</span>
    </Footer>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    const useridRule = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("账号不能为空"));
      } else if (this.userType == "student" && value.length != 12) {
        return callback(new Error("请输入正确的学号"));
      } else {
        callback();
      }
    };
    const passwordRule = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("密码不能为空"));
      } else if (value.length < 6 || value.length > 30) {
        return callback(new Error("密码长度应在6-30个字符之间"));
      } else {
        callback();
      }
    };
    return {
      formData: {
        userid: "",
        password: ""
      },
      userType: "student",
      loginRule: {
        userid: [{ validator: useridRule, trigger: "blur" }],
        password: [{ validator: passwordRule, trigger: "blur" }]
      }
    };
  },
  methods: {
    getSelectValue: function(name) {
      this.userType = name;
    },
    submit: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$Message.success("登陆成功");
          this.$router.replace({
            path: '/main'
          })
        }
      });
    }
  }
};
</script>

<style scoped>
.footer {
  text-align: center;
  position: absolute;
  width: 100%;
  bottom: 0;
}
.footer span {
  margin: 5%;
}
.login-title {
  color: white;
  text-align: center;
}
.login-content {
  width: 450px;
  margin: 50px auto;
}
.ivu-menu-item {
  margin: auto;
  width: 150px;
  text-align: center;
}
.form {
  margin: 50px auto;
  width: 350px;
}
</style>