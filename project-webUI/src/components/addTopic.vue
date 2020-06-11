<template>
  <div class="wrap">
    <div class="title">
      <h1>添加课题</h1>
      <p style="color:#5cadff">注：每位导师最多可发布20个课题</p>
    </div>

    <Form ref="AddTopic" :model="topicInfo" :rules="topicRule">
      <FormItem label="课题名" prop="TopicName">
        <Input v-model="topicInfo.TopicName" placeholder="课题名称"></Input>
      </FormItem>
      <FormItem label="课题类型" prop="Type">
        <Select @on-change="TypeChange" v-model="topicInfo.Type">
          <Option v-for="(value,key) in type" :value="key" :key="key">{{value}}</Option>
        </Select>
      </FormItem>
      <FormItem label="课题介绍" prop="Introduction">
        <Input
          v-model="topicInfo.Introduction"
          placeholder="填写3-800字的课题介绍"
          type="textarea"
          maxlength="800"
          show-word-limit
          :rows="6"
          :autosize="{minRows:6}"
        ></Input>
      </FormItem>
      <Button type="primary" long class="Top30" @click="topicSubmit('AddTopic')">添加</Button>
    </Form>
  </div>
</template>

<script>
export default {
  name: "AddTopic",
  data() {
    return {
      type: {},
      topicInfo: {
        TopicName: "",
        Type: "",
        Introduction: ""
      },
      topicRule: {
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
          { required: true, message: "必须选择课题类型", trigger: "blur" }
        ],
        Introduction: [
          { required: true, message: "课题介绍不能为空", trigger: "blur" },
          {
            min: 3,
            max: 800,
            message: "课题介绍长度应在3-800个字符之间",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.axios
      .get("/getType")
      .then(response => {
        if (response.data.status == 200) this.type = response.data.type;
        else this.$Message.error("课题类型获取失败");
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
  },
  methods: {
    TypeChange: function(value) {
      this.topicInfo.Type = value;
    },
    topicSubmit: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios({
            method: "post",
            url: "/addTopic",
            params: {
              accessToken: localStorage.getItem("access_token")
            },
            data: {
              topicName: this.topicInfo.TopicName,
              type: this.topicInfo.Type,
              introduction: this.topicInfo.Introduction
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.$Message.success("添加成功");
              } else if (status == 401) {
                this.$Message.error(data.message);
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
          this.$Message.error("请检查信息");
        }
      });
    }
  }
};
</script>

<style scoped>
.wrap {
  width: 400px;
  margin: auto;
}
.title {
  text-align: center;
  margin: 30px;
}
.Top30 {
  margin-top: 30px;
}
</style>