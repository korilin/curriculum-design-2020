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
          <Option v-for="(teacher,index) in teachers" :value="teacher.TID" :key="index" @click.native="teacherChange(index)">{{teacher.TName}}</Option>
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
        TID: ""
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
    this.teachers = [
      { TID: "10001", TName: "teacher1", TopicDemand: "无要求" },
      {
        TID: "10002",
        TName: "teacher2",
        TopicDemand:
          "视图是一个由查询定义内容的虚拟表, 和基本表差不多, 不过在数据库中并不上以数据值存储集形式存在的, 除非是索引视图。"
      },
      {
        TID: "10003",
        TName: "teacher3",
        TopicDemand:
          "Nginx可以用来做Web服务器或者反向代理，当Nginx作为反向代理软件时，每个网络请求都会先由Nginx接收，Nginx会根据配置文件里的配置对请求进行过滤处理，等请求完全接收完再发送给上游服务器一次性处理，从而可以提高上游服务器的工作性"
      }
    ];
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
      this.applyForm.TID = this.teachers[index].TID;
      this.demand = this.teachers[index].TopicDemand;
      this.showDemand = true;
    },
    typeChange: function(key) {
      this.applyForm.Type = key;
    },
    applySubmit: function(name) {
      this.$refs[name].validate((valid,error) => {
        if (valid) {
          this.$Message.success("已提交申请");
        }
        if(error){
          console.log(error)
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