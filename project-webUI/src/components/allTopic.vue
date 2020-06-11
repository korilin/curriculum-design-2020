<template>
  <div class="wrap">
    <div span="8" v-for="topic in topics" :key="topic.TopicName" class="cardWrap">
      <Card class="card">
        <p slot="title" class="bold" style="color:#2b85e4">
          {{topic.topicName}}
          <Icon type="ios-paper" size="20" style="margin-left:20px" />
        </p>
        <Row class="row left">
          <Col span="2" class="bold">导师:</Col>
          <Col span="22">{{topic.tname}}</Col>
        </Row>
        <Row class="row left">
          <Col span="2" class="bold">类型:</Col>
          <Col span="22">{{topic.typeName}}</Col>
        </Row>
        <Row class="row left">
          <Col span="2" class="bold">介绍:</Col>
          <Col span="22">{{topic.introduction}}</Col>
        </Row>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: "AllTopic",
  data() {
    return {
      topics: []
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
        if (response.data.status == 200)
          this.topics = response.data.selectableTopicInfos;
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
.wrap {
  margin: 20px;
}
.row {
  text-align: center;
  width: 100%;
}
.card {
  width: 90%;
  margin: auto;
  min-width: 300px;
}
.left {
  text-align: left;
  margin-top: 10px;
}
.bold {
  font-weight: bold;
}
.col {
  margin: 10px auto;
}
.cardWrap {
  width: 100%;
  margin-bottom: 30px;
}
</style>