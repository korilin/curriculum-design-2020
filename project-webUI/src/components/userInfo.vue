<template>
  <div class="user_info">
    <template v-if="userType=='Student'">学生</template>

    <template v-if="userType=='Teacher'">
      <div class="info_wrap teacher_info">
        <h1>您好，导师！</h1>
        <Form
          ref="TeacherInfo"
          :model="TeacherInfo"
          :rules="teacherRule"
          :label-width="100"
          label-position="left"
          v-if="TeacherInfo&&professions[TeacherInfo.guideProfID]"
        >
          <FormItem label="姓名">
            <Input v-model="TeacherInfo.name" disabled></Input>
          </FormItem>
          <FormItem label="工号">
            <Input v-model="TeacherInfo.tid" disabled></Input>
          </FormItem>
          <FormItem label="职位">
            <Input v-model="TeacherInfo.position" disabled></Input>
          </FormItem>
          <FormItem label="职称">
            <Input v-model="TeacherInfo.trank" disabled></Input>
          </FormItem>
          <FormItem label="指导专业">
            <Input v-model="professions[TeacherInfo.guideProfID].name" disabled></Input>
          </FormItem>
          <FormItem label="联系电话" prop="phone">
            <Input v-model="TeacherInfo.phone" maxlength="11" type="tel" number></Input>
          </FormItem>
          <FormItem label="联系邮箱" prop="email">
            <Input v-model="TeacherInfo.email" type="email"></Input>
          </FormItem>
          <FormItem label="自选课题要求" prop="topicDemand">
            <Input
              v-model="TeacherInfo.topicDemand"
              required
              type="textarea"
              :autosize="{minRows:5}"
            ></Input>
          </FormItem>
          <FormItem>
            <Button
              type="success"
              style="margin-right:20px"
              @click="changeInfoT('TeacherInfo')"
            >保存修改</Button>
            <Button type="primary" @click="changePassword">修改密码</Button>
          </FormItem>
        </Form>
      </div>
    </template>

    <template v-if="userType=='Admin'">
      <div class="info_wrap admin_info">
        <h1>您好，管理员{{AdminInfo.adminId}}！</h1>
        <Form
          :label-width="100"
          label-position="left"
          ref="passwordForm"
          :model="passwordForm"
          :rules="passwordRule"
        >
          <FormItem label="输入旧密码" prop="oldPassword">
            <Input v-model="passwordForm.oldPassword" type="password" placeholder="旧密码"></Input>
          </FormItem>
          <FormItem label="输入新密码" prop="newPassword">
            <Input v-model="passwordForm.newPassword" type="password" placeholder="新密码"></Input>
          </FormItem>
          <FormItem label="确认新密码" prop="twoPassword">
            <Input v-model="passwordForm.twoPassword" type="password" placeholder="再次输入密码"></Input>
          </FormItem>
        </Form>
        <div class="admin_button">
          <Button type="primary" @click="changePassword('passwordForm')">修改密码</Button>
        </div>
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
    const validatePass = (rule, value, callback) => {
      if (value === "") callback(new Error("密码不能为空"));
      else if (value.length < 6 || value.length > 30)
        callback(new Error("密码长度应在6-30个字符之间"));
      else callback();
    };
    const validateTwoPass = (rule, value, callback) => {
      if (value != this.passwordForm.newPassword)
        callback(new Error("两次输入的密码不一致"));
      else callback();
    };
    const validateDemand = (rule, value, callback) => {
      if (value.length == 0) callback(new Error("自选课题要求不能为空"));
      else callback();
    };
    return {
      professions: {},
      AdminInfo: {},
      TeacherInfo: {},
      StudentInfo: {},
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        twoPassword: ""
      },
      passwordRule: {
        oldPassword: [{ validator: validatePass, trigger: "blur" }],
        newPassword: [{ validator: validatePass, trigger: "blur" }],
        twoPassword: [
          { validator: validatePass, trigger: "blur" },
          { validator: validateTwoPass, trigger: "blur" }
        ]
      },
      teacherRule: {
        phone: [
          { min: 10000000000, max: 99999999999, message: "请输入正确格式的电话号码", trigger: "blur", type: "number"}
        ],
        email: [
          { type: "email", message: "请输入有效的邮箱", trigger: "blur" }
        ],
        topicDemand: [{ validator: validateDemand, trigger: "blur" }]
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
      url: "/userInfo",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var data = response.data;
        if (data.status == 200) {
          if (this.userType === "Admin") this.AdminInfo = data.adminInfo;
          if (this.userType === "Teacher") this.TeacherInfo = data.teacherInfo;
          if (this.userType === "Student") this.StudentInfo = data.studentInfo;
        } else if (status == 401) {
          this.$Message.error(response.data.message);
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
    changeInfoT: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios({
            method: "post",
            url: "/teacher/changeInfo",
            params: {
              accessToken: localStorage.getItem("access_token"),
              phone: this.TeacherInfo.phone,
              email: this.TeacherInfo.email,
              topicDemand: this.TeacherInfo.topicDemand
            }
          })
            .then(response => {
              var data = response.data;
              if (data.status == 204) {
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
            })
            .catch(error => {
              this.$Message.error(error.message);
              console.log(error);
            });
        } else {
          this.$Message.error("请检查填写的信息");
        }
      });
    },
    changeInfoS: function() {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios({
            method: "post",
            url: "/student/changeInfo",
            params: {
              accessToken: localStorage.getItem("access_token")
            }
          })
            .then(response => {
              var data = response.data;
              if (data.status == 204) {
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
            })
            .catch(error => {
              this.$Message.error(error.message);
              console.log(error);
            });
        } else {
          this.$Message.error("请检查填写的信息");
        }
      });
    },
    changePassword: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          var password;
          if (this.userType == "Admin") password = this.AdminInfo.password;
          else if (this.userType == "Student")
            password = this.StudentInfo.password;
          else if (this.userType == "Teacher")
            password = this.TeacherInfo.password;
          if (this.passwordForm.oldPassword === password) {
            var newPassword = this.passwordForm.newPassword;
            this.axios({
              method: "post",
              url: "/changePassword",
              params: {
                accessToken: localStorage.getItem("access_token"),
                password: newPassword
              }
            })
              .then(response => {
                var data = response.data;
                if (data.status == 204) {
                  if (this.userType == "Admin")
                    this.AdminInfo.password = newPassword;
                  else if (this.userType == "Student")
                    this.StudentInfo.password = newPassword;
                  else if (this.userType == "Teacher")
                    this.TeacherInfo.password = newPassword;
                  this.passwordForm.oldPassword = "";
                  this.passwordForm.newPassword = "";
                  this.passwordForm.twoPassword = "";
                  this.$Message.success("修改成功");
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
          } else {
            this.$Message.error("旧密码错误");
          }
        } else {
          this.$Message.error("请检查填写的密码");
        }
      });
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
  margin-top: 50px;
}
.admin_id {
  margin-top: 50px;
  font-size: 18px;
  font-weight: bold;
}
</style>