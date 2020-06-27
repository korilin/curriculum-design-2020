<template>
  <div class="add_teacher">
    <h1>添加导师</h1>
    <Form
      ref="AddTeacher"
      :model="teacherInfo"
      :rules="teacherRule"
      :label-width="100"
      label-position="left"
    >
      <div class="teacher_input">
        <div class="input_left">
          <FormItem label="工号" prop="TID">
            <Input v-model="teacherInfo.TID" placeholder="教师工号" />
          </FormItem>
          <FormItem label="姓名" prop="TName">
            <Input v-model="teacherInfo.TName" placeholder="真实姓名" />
          </FormItem>
          <FormItem label="指导专业" prop="GuideProfession">
            <Select @on-change="guideProfessionChange">
              <Option v-for="(value,key) in professions" :value="key" :key="key">{{value.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="密码" prop="Password">
            <Input v-model="teacherInfo.Password" placeholder="默认密码" />
          </FormItem>
        </div>
        <div class="input_right">
          <FormItem label="职位" prop="Position">
            <Select v-model="teacherInfo.Position" placeholder="职位">
              <Option value="无">无</Option>
              <Option value="教师">教师</Option>
              <Option value="教导主任">教导主任</Option>
              <Option value="总务主任">总务主任</Option>
              <Option value="教研室主任">教研室主任</Option>
              <Option value="系主任">系主任</Option>
              <Option value="学院院长">学院院长</Option>
              <Option value="校长助理">校长助理</Option>
              <Option value="副校长">副校长</Option>
              <Option value="书记">书记</Option>
              <Option value="校长">校长</Option>
            </Select>
          </FormItem>
          <FormItem label="职称" prop="Rank">
            <Select @on-change="RankChange">
              <Option value="无">无</Option>
              <Option value="教授">教授</Option>
              <Option value="副教授">副教授</Option>
              <Option value="研究员">研究员</Option>
              <Option value="讲师">讲师</Option>
              <Option value="助教">助教</Option>
            </Select>
          </FormItem>
          <FormItem label="电话" prop="Phone">
            <Input v-model="teacherInfo.Phone" type="tel" number placeholder="联系电话" />
          </FormItem>
          <FormItem label="邮箱" prop="Email">
            <Input v-model="teacherInfo.Email" placeholder="联系邮箱" type="email" />
          </FormItem>
        </div>
      </div>
      <div style="text-align:center">
        <Button
          type="success"
          @click="addTeacher('AddTeacher')"
          style="margin-top:30px;width:300px"
        >添加</Button>
      </div>
    </Form>
  </div>
</template>

<script>
export default {
  name: "AddTeacher",
  data() {
    return {
      teacherInfo: {
        TID: "",
        TName: "",
        Position: "",
        Rank: "",
        GuideProfession: "",
        Phone: "",
        Email: "",
        Password: "123456"
      },
      teacherRule: {
        TID: [
          { required: true, message: "工号不能为空", trigger: "blur" },
          { min: 5, max: 12, message: "工号长度应在5-12之间", trigger: "blur" }
        ],
        TName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "姓名长度应在2-10个字符之间",
            trigger: "blur"
          }
        ],
        GuideProfession: [
          { required: true, message: "指导专业必须指定", trigger: "blur" }
        ],
        Password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          {
            min: 6,
            max: 30,
            message: "密码长度应在6-30个字符之间",
            trigger: "blur"
          }
        ],
        Phone: [
          { min: 10000000000, max: 99999999999, message: "请输入正确的电话", trigger: "blur", type: "number"}
        ],
        Email: [
          {message:"请输入正确的邮箱", trigger:"blur", type:"email"}
        ]
      },
      professions: []
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
      });
  },
  methods: {
    addTeacher: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          var info = this.teacherInfo;
          this.axios
            .post("/addTeacher", {
              tid: info.TID,
              name: info.TName,
              position: info.Position,
              rank: info.Rank,
              guideProfId: info.GuideProfession,
              phone: info.Phone,
              email: info.Email,
              password: info.Password,
              accessToken: localStorage.getItem("access_token")
            })
            .then(response => {
              var status = response.data.status;
              if (status == 204) this.$Message.success("添加成功");
              else if (status == 401) {
                this.$Message.error(response.data.message);
                localStorage.removeItem("access_token");
                this.$router.replace({
                  name: "Login"
                });
              } else {
                this.$Message.error(response.data.message);
              }
            })
            .catch(error => {
              this.$Message.error(error.message);
            });
        } else {
          this.$Message.error("请检查填写的信息");
        }
      });
    },
    guideProfessionChange: function(key) {
      this.teacherInfo.GuideProfession = key;
    },
    RankChange: function(value) {
      this.teacherInfo.Rank = value;
    }
  }
};
</script>

<style scoped>
.add_teacher {
  width: 800px;
  margin: 30px auto;
}
.add_teacher h1 {
  text-align: center;
  margin-bottom: 30px;
}
.teacher_input {
  display: flex;
}
.input_left,
.input_right {
  width: 300px;
  margin: 0 50px;
}
.ivu-form-item-content {
  margin-left: 0 !important;
}
</style>