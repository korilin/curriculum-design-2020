<template>
  <div v-if="topicInfo==0">
    <h1></h1>
  </div>
  <div v-else-if="teacherInfo==null">
    <h1>您还没有导师，赶紧去寻找导师吧！</h1>
  </div>
  <div v-else class="topic">
    <Card style="padding: 10px 20px 20px;">
      <div style="color:#2db7f5;font-size:24px;font-weight:bold;text-align:center" slot="title">我的导师</div>
      <div class="info">
        <span class="label">名称：</span>
        <span class="content">{{teacherInfo.name}}</span>
      </div>
      <div class="info">
        <span class="label">职位：</span>
        <span class="content">{{teacherInfo.position}}</span>
      </div>
      <div class="info">
        <span class="label">职称：</span>
        <span class="content">{{teacherInfo.trank}}</span>
      </div>
      <div class="info">
        <span class="label">电话：</span>
        <span class="content">{{teacherInfo.phone}}</span>
      </div>
      <div class="info">
        <span class="label">邮箱：</span>
        <span class="content">{{teacherInfo.email}}</span>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: "MyTeacher",
  data() {
    return {
      teacherInfo: 0
    };
  },
  created: function() {
    this.axios({
      method: "get",
      url: "/getMyTeacher",
      params: {
        accessToken: localStorage.getItem("access_token")
      }
    })
      .then(response => {
        var status = response.data.status;
        console.log(response.data);
        if (status == 200 || status == 406)
          this.teacherInfo = response.data.teacherInfo;
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
  }
};
</script>

<style scoped>
.topic {
  font-weight: bold;
  width: 600px;
  margin: 30px auto;
}
.info {
  margin-top: 30px;
  font-size: 18px;
}
.content {
  display: inline-block;
  width: 300px;
  text-align: center;
}
h1,
h2 {
  text-align: center;
  margin: 30px auto 50px;
}
.label {
  display: inline-block;
  width: 200px;
  text-align: center;
}
</style>