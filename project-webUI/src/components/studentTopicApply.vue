<template>
  <div class="wrap">
    <h1>课题申请</h1>
    <Form
      ref="ApplyForm"
      :model="applyForm"
      :rules="ApplyRule"
      label-position="left"
      :label-width="100"
    >
      <FormItem label="选择导师" prop="TID">
        <Select v-model="applyForm.TID" placeholder="请选择导师">
          <Option
            v-for="(teacher,index) in teachers"
            :value="teacher.tid"
            :key="index"
            @click.native="teacherChange(index)"
          >{{teacher.name}}</Option>
        </Select>
      </FormItem>
      <div v-show="showDemand" class="demandWrap">
        <strong>导师自选课题要求：</strong>
        <Card class="demand">{{demand}}</Card>
      </div>
      <FormItem label="课题名称" prop="TopicName">
        <Input v-model="applyForm.TopicName" placeholder="课题名称"></Input>
      </FormItem>
      <FormItem label="课题类型" prop="Type">
        <Select v-model="applyForm.Type" @on-change="typeChange">
          <Option v-for="(value,key) in type" :value="key" :key="key">{{value}}</Option>
        </Select>
      </FormItem>
      <FormItem label="课题介绍" prop="Introduction">
        <Input
          v-model="applyForm.Introduction"
          type="textarea"
          :autosize="{minRows:6}"
          placeholder="请填写3-800字的课题介绍"
        ></Input>
      </FormItem>
      <div style="margin:30px auto;width:300px">
        <Button style="margin:30px auto;" type="primary" long @click="applySubmit('ApplyForm')">提交申请</Button>
      </div>
    </Form>
  </div>
</template>

<script>
export default {
  name: "StudentTopicApply",
  data() {
    return {
      teachers: [],
      type: {},
      demand: "",
      showDemand: false,
      applyForm: {
        TopicName: "",
        Type: "",
        Introduction: "",
        TID: "0"
      },
      ApplyRule: {
        TopicName: [
          { required: true, message: "课题名不能为空", trigger: "blur" },
          {
            min: 3,
            max: 30,
            message: "课题名长度应在3-30个字符之间",
            trigger: "blur"
          }
        ],
        Type: [
          { required: true, message: "必须选择课题类型", trigger: "change" }
        ],
        Introduction: [
          { required: true, message: "课题介绍不能为空", trigger: "blur" },
          {
            min: 3,
            max: 800,
            message: "课题介绍长度应在3-800个字符之间",
            trigger: "blur"
          }
        ],
        TID: [{ required: true, message: "必须选择导师", trigger: "change" }]
      }
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getSelectableTeacher",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        if (response.data.status == 200) this.teachers = response.data.teachers;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        } else this.$Message.error(response.data.message);
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
    this.type = {
      10001: "工程设计类",
      10002: "理论研究类",
      10003: "应用（实验）研究类",
      10004: "软件设计类",
      10005: "其它"
    };
  },
  methods: {
    teacherChange: function(index) {
      this.applyForm.TID = this.teachers[index].tid;
      this.demand = this.teachers[index].topicDemand;
      this.showDemand = true;
    },
    typeChange: function(key) {
      this.applyForm.Type = key;
    },
    applySubmit: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios({
            method: "post",
            url: "/applyStudentTopic",
            params: {
              accessToken: localStorage.getItem("access_token")
            },
            data: {
              topicName: this.applyForm.TopicName,
              typeId: this.applyForm.Type,
              introduction: this.applyForm.Introduction,
              tid: this.applyForm.TID
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                console.log();
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
h1 {
  text-align: center;
  margin: 30px auto 50px;
}
.wrap {
  margin: 30px auto;
  width: 500px;
}
.demandWrap {
  margin: 10px auto 50px;
}

.demand {
  margin: 20px;
  padding: 10px;
}
</style>