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
      <span class="content">{{topicInfo.topicName}}</span>
    </div>
    <div class="info">
      课题类型：
      <span class="content">{{type.typeName}}</span>
    </div>
    <div class="info">
      课题导师：
      <span class="content">{{TName}}</span>
    </div>
    <div class="info">
      课题信息：
      <Card class="info intro">
        <p>{{topicInfo.introduction}}</p>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyTopic",
  data() {
    return {
      topicInfo: 0,
      type: "",
      TName: ""
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
        console.log(response.data);
        if (status == 200) {
          this.type = response.data.type;
          this.topicInfo = response.data.topic;
          this.TName = response.data.name;
        } else if (status == 401) {
          this.$Message.error(response.data.message);
          localStorage.removeItem("access_token");
          this.$router.replace({
            name: "Login"
          });
        }else if (status == 406) this.topicInfo = null;
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