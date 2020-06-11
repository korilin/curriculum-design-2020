<template>
  <div v-if="topicInfo==null">
    <h1>您还没有申请通过的课题，赶紧去申请课题吧！</h1>
  </div>
  <div v-else class="topic">
    <h1 style="color:#19be6b">已通过课题</h1>
    <div class="info">
      课题名称：
      <span class="content">{{topicInfo.TopicName}}</span>
    </div>
    <div class="info">
      课题类型：
      <span class="content">{{topicInfo.Type}}</span>
    </div>
    <div class="info">
      课题导师：
      <span class="content">{{topicInfo.TName}}</span>
    </div>
    <div class="info">
      课题信息：
      <Card class="info intro">
        <p>{{topicInfo.Introduction}}</p>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyTopic",
  data() {
    return {
      topicInfo: {}
    };
  },
  created() {
    this.axios({
      method: "get",
      url: "/getAllowTopic",
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
    this.topicInfo = {
      TopicName: "毕业设计选题系统",
      TName: "老师一",
      Type: "应用（实验）研究类",
      Introduction:
        "外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
    };
  }
};
</script>

<style scoped>
.topic {
  font-weight: bold;
  font-size: 16px;
  width: 600px;
  margin: auto;
}
.info {
  margin-top: 30px;
}
.intro {
  padding: 10px;
  font-size: 16px;
  line-height: 30px;
}
.content {
  display: inline-block;
  width: 500px;
  text-align: center;
}
h1 {
  text-align: center;
  margin: 30px auto 50px;
}
</style>