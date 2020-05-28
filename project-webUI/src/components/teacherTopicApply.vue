<template>
  <div class="wrap">
    <h1>课题申请</h1>
    <Form ref="ApplyForm" :model="applyForm" :rules="ApplyRule">
      <FormItem label="选择导师" prop="TID">
        <Select v-model="applyForm.TID" @on-change="teacherChange">
          <Option
            v-for="teacher in teachers"
            :value="teacher.TID"
            :key="teacher.TID"
          >{{teacher.TName}}</Option>
        </Select>
      </FormItem>
      <FormItem label="选择课题" prop="TopicID">
        <Select v-model="applyForm.TopicID" @on-change="topicChange">
          <Option
            v-for="topic in topics"
            :value="topic.TopicID"
            :key="topic.TopicID"
          >{{topic.TopicName}}</Option>
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
        TopicID: [{required: true, message:"必须选择课题", trigger:"change", type:"number"}]
      }
    };
  },
  created() {
    this.teachers = [
      { TID: "10001", TName: "teacher1" },
      { TID: "10002", TName: "teacher2" },
      { TID: "10003", TName: "teacher3" }
    ];
  },
  methods: {
    teacherChange: function(TID) {
      this.applyForm.TID = TID;

      this.topics = [
        { TopicID: 10101, TopicName: "topic1" },
        { TopicID: 10102, TopicName: "topic2" },
        { TopicID: 10103, TopicName: "topic3" }
      ];
    },
    topicChange: function(TopicID) {
      this.applyForm.TopicID = TopicID;
    },
    applySubmit: function(name){
        this.$refs[name].validate(valid=>{
            if(valid){
                this.$Message.success("已提交申请");
            }
        })
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