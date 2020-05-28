<template>
  <div v-if="teacherInfo==null">
    <h1>您还没有导师，赶紧去寻找导师吧！</h1>
  </div>
  <div v-else class="topic">
    <Card style="padding: 10px 20px 20px;">
      <div style="color:#2db7f5;font-size:24px;font-weight:bold;text-align:center" slot="title">我的导师</div>
      <div class="info">
        <span class="label">名称：</span>
        <span class="content">{{teacherInfo.TName}}</span>
      </div>
      <div class="info">
        <span class="label">职位：</span>
        <span class="content">{{teacherInfo.Position}}</span>
      </div>
      <div class="info">
        <span class="label">职称：</span>
        <span class="content">{{teacherInfo.Rank}}</span>
      </div>
      <div class="info">
        <span class="label">电话：</span>
        <span class="content">{{teacherInfo.Phone}}</span>
      </div>
      <div class="info">
        <span class="label">邮箱：</span>
        <span class="content">{{teacherInfo.Email}}</span>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: "MyTeacher",
  data() {
    return {
      teacherInfo: {}
    };
  },
  created: function() {
    this.teacherInfo = {
      TName: "导师一",
      Position: "教研室主任",
      Rank: "教授",
      Phone: "12345678900",
      Email: "123456@qq.com"
    };
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