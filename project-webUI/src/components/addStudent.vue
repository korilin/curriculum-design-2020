<template>
  <div class="add_student">
    <h1>添加学生</h1>
    <Form
      ref="AddStudent"
      :model="studentInfo"
      :rules="studentRule"
      :label-width="100"
      label-position="left"
    >
      <FormItem label="学号" prop="SID">
        <Input v-model="studentInfo.SID" placeholder="输入学生学号" />
      </FormItem>
      <FormItem label="姓名" prop="SName">
        <Input v-model="studentInfo.SName" placeholder="真实姓名" />
      </FormItem>
      <FormItem label="年级" prop="Grade">
        <DatePicker
          @on-change="gradeChange"
          placeholder="入学年份"
          type="year"
          format="yyyy"
          :clearable="false"
          :editable="false"
          style="width:200px"
        />
      </FormItem>
      <FormItem label="院系" prop="department">
        <Select v-model="studentInfo.department" placeholder="选择院系">
          <Option v-for="value in departments" :value="value" :key="value">{{value}}</Option>
        </Select>
      </FormItem>
      <FormItem label="专业" prop="Profession">
        <Select @on-change="professionChange">
          <template v-for="(value,key) in professions">
            <Option
              v-if="studentInfo.department?(value.deptName==studentInfo.department):false"
              :value="key"
              :key="key"
            >{{value.name}}</Option>
          </template>
        </Select>
      </FormItem>
      <FormItem label="班号" prop="ClassNumber">
        <Select v-model="studentInfo.ClassNumber" placeholder="学生班级">
          <Option value="1">1</Option>
          <Option value="2">2</Option>
          <Option value="3">3</Option>
          <Option value="4">4</Option>
          <Option value="5">5</Option>
          <Option value="6">6</Option>
          <Option value="7">7</Option>
          <Option value="8">8</Option>
          <Option value="9">9</Option>
          <Option value="10">10</Option>
          <Option value="11">11</Option>
          <Option value="12">12</Option>
        </Select>
      </FormItem>
      <FormItem label="密码" prop="Password">
        <Input v-model="studentInfo.Password" placeholder="默认密码" />
      </FormItem>
      <div style="text-align:center">
        <Button
          type="success"
          @click="addStudent('AddStudent')"
          style="margin-top:30px;width:300px"
        >添加</Button>
      </div>
    </Form>
  </div>
</template>

<script>
export default {
  name: "AddStudent",
  data() {
    return {
      studentInfo: {
        SID: "",
        SName: "",
        department: false,
        Grade: "",
        Profession: "",
        ClassNumber: "",
        Password: "123456"
      },
      studentRule: {
        SID: [
          { required: true, message: "学号不能为空", trigger: "blur" },
          { min: 12, max: 12, message: "请输入正确的学号", trigger: "blur" }
        ],
        SName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "姓名长度应在2-10个字符之间",
            trigger: "blur"
          }
        ],
        Grade: [
          {
            required: true,
            message: "年级不能为空",
            trigger: "change",
            type: "number"
          },
          {
            max: 2020,
            message: "不能超过年份2020",
            trigger: "change",
            type: "number"
          }
        ],
        Profession: [
          { required: true, message: "专业不能为空", trigger: "change" }
        ],
        department: [
          { required: true, message: "院系不能为空", trigger: "change" }
        ],
        ClassNumber: [
          { required: true, message: "年级不能为空", trigger: "change" }
        ],
        Password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          {
            min: 6,
            max: 30,
            message: "密码长度应在6-30个字符之间",
            trigger: "blur"
          }
        ]
      },
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
      ]
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
    addStudent: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios
            .post("/addStudent", {
              sid: this.studentInfo.SID,
              name: this.studentInfo.SName,
              grade: this.studentInfo.Grade,
              professionId: this.studentInfo.Profession,
              classNumber: this.studentInfo.ClassNumber,
              password: this.studentInfo.Password,
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
    gradeChange: function(value) {
      this.studentInfo.Grade = parseInt(value);
    },
    professionChange: function(key) {
      this.studentInfo.Profession = key;
    }
  }
};
</script>

<style scoped>
.add_student {
  width: 300px;
  margin: 30px auto;
}
.add_student h1 {
  text-align: center;
  margin-bottom: 30px;
}
</style>