<template>
  <div class="wrap">
    <h1>课题申请</h1>
    <Form ref="ApplyForm" :model="applyForm" :rules="ApplyRule">
      <FormItem label="选择导师" prop="TID">
        <Select v-model="applyForm.TID" @on-change="teacherChange">
          <Option
            v-for="teacher in teachers"
            :value="teacher.tid"
            :key="teacher.tid"
          >{{teacher.name}}</Option>
        </Select>
      </FormItem>
      <FormItem label="选择课题" prop="TopicID">
        <Select v-model="applyForm.TopicID" @on-change="topicChange">
          <template v-for="topic in topics">
            <Option
              v-if="applyForm.TID!=''&&topic.tid==applyForm.TID"
              :value="topic.topicId"
              :key="topic.topicId"
            >{{topic.topicName}}</Option>
          </template>
        </Select>
      </FormItem>
      <FormItem>
        <Button type="primary" style="margin:30px auto" long @click="applySubmit('ApplyForm')">提交申请</Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
export default {
  name: "TeacherTopicApply",
  data() {
    return {
      teachers: [],
      topics: [],
      applyForm: {
        TID: "",
        TopicID: ""
      },
      ApplyRule: {
        TID: [{ required: true, message: "必须选择导师", trigger: "change" }],
        TopicID: [
          { required: true, message: "必须选择课题", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getSelectableTopic",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        if (status == 200) this.topics = response.data.selectableTopicInfos;
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

    this.axios({
      method: "get",
      url: "/getSelectableTeacher",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        if (status == 200) this.teachers = response.data.teachers;
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
  },
  methods: {
    teacherChange: function(TID) {
      this.applyForm.TID = TID;
    },
    topicChange: function(TopicID) {
      this.applyForm.TopicID = TopicID;
    },
    applySubmit: function(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.axios({
            method: "post",
            url: "/applyTeacherTopic",
            params: {
              accessToken: localStorage.getItem("access_token"),
              topicId: this.applyForm.TopicID
            }
          })
            .then(response => {
              var status = response.data.status;
              var data = response.data;
              if (status == 204) {
                this.$Message.success("申请成功");
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
  margin: 50px auto;
  text-align: center;
}
.wrap {
  width: 300px;
  margin: 30px auto;
}
</style>