<template>
  <div class="add_student">
    <h1>从文件导入教师</h1>
    <div class="upload_wrap">
      <Upload
        action="http://127.0.0.1:5000/teacher/upload"
        type="drag"
        :show-upload-list="true"
        accept=".xls, .xlsx"
        :on-success="uploadSuccess"
        multiple
        class="upload"
      >
        <div class="upload_text">
          <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
          <p>选择文件或将文件拖到此处</p>
          <p>只能上传.xls或.xlsx文件</p>
        </div>
      </Upload>
      <Card class="card" v-if="error_number+success_number!=0">
        <p slot="title" style="font-weight:bold">导入情况</p>
        <p style="color:#19be6b;margin-bottom: 10px;">成功行数：{{success_number}}</p>
        <p style="color:#ed4014;margin-bottom: 10px;">失败行数：{{error_number}}</p>
        <p style="margin-bottom: 10px;" v-for="(error,index) in errors" :key="index">{{error}}</p>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImportTeacher",
  data() {
    return {
      error_number: 0,
      success_number: 0,
      errors: []
    };
  },
  methods: {
    uploadSuccess: function(response) {
      console.log(response);
      this.error_number += response.error_number;
      this.success_number += response.success_number;
      this.errors = this.errors.concat(response.errors);
    }
  }
};
</script>

<style scoped>
.add_student {
  width: 800px;
  margin: 30px auto;
}
.add_student h1 {
  text-align: center;
  margin-bottom: 30px;
}
.upload_wrap {
    width: 100%;
}
.upload {
    width: 500px;
    margin: auto;
}
.upload_text {
    padding: 30px 80px;
    font-size: 12px;
}
.card {
    margin-top: 50px;
}
</style>