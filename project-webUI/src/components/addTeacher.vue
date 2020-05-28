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
              <Option v-for="profession in professions" :value="profession" :key="profession"></Option>
            </Select>
          </FormItem>
          <FormItem label="密码" prop="Password">
            <Input v-model="teacherInfo.Password" placeholder="默认密码" />
          </FormItem>
        </div>
        <div class="input_right">
          <FormItem label="职位" prop="Position">
            <Input v-model="teacherInfo.Position" placeholder="职位" />
          </FormItem>
          <FormItem label="职称" prop="Rank">
             <Select @on-change="RankChange">
              <Option value="教授">教授</Option>
              <Option value="副教授">副教授</Option>
              <Option value="讲师">讲师</Option>
              <Option value="助教">助教</Option>
            </Select>
          </FormItem>
          <FormItem label="电话" prop="Phone">
            <Input v-model="teacherInfo.Phone" placeholder="联系电话" />
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
        TID: [{ required: true, message: "工号不能为空", trigger: "blur" }],
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
        ]
      },
      professions: ["软件工程", "计算机与信息科学"]
    };
  },
  methods: {
    addTeacher: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$Message.success("添加成功");
        } else {
          this.$Message.error("检查？？");
        }
      });
    },
    guideProfessionChange: function(value) {
      this.teacherInfo.GuideProfession = value;
    },
    RankChange: function(value){
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