<template>
  <div v-if="topicInfo==0">
    <h1></h1>
  </div>
  <div v-else-if="topicInfo==null">
    <h1>您还没有通过申请的课题，赶紧去申请课题吧！</h1>
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
      topicInfo: 0
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
        if (status == 200) this.topicInfo = response.data.topicInfo;
        else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        }
        if (status == 406) this.topicInfo = null;
        else this.$Message.error(response.data.message);
      })
      .catch(error => {
        this.$Message.error(error.message);
        console.log(error);
      });
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