<template>
    <div class="wrap">
      <div span="8" v-for="teacher in teachers" :key="teacher.TName" class="cardWrap">
        <Card class="card">
          <p slot="title" class="bold" style="text-align:center;color:#2b85e4"><Icon type="md-person" size="20" style="margin-right:20px"/>{{teacher.TName}}</p>
          <Row class="row left">
            <Col span="4" class="bold">职位:</Col>
            <Col span="20">{{teacher.Position}}</Col>
          </Row>
          <Row class="row left">
            <Col span="4" class="bold">职称:</Col>
            <Col span="20">{{teacher.Rank}}</Col>
          </Row>
          <Row class="row left">
            <Col span="4" class="bold">电话:</Col>
            <Col span="20">{{teacher.Phone}}</Col>
          </Row>
          <Row class="row left">
            <Col span="4" class="bold">邮箱:</Col>
            <Col span="20">{{teacher.Email}}</Col>
          </Row>
          <Row class="row left">
            <Col span="4" class="bold">自选课题要求:</Col>
            <Col span="20">{{teacher.TopicDemand}}</Col>
          </Row>
        </Card>
      </div>
  </div>
</template>

<script>
    export default {
        name: 'AllTeacher',
        data() {
            return {
                teachers: []
            }
        },
        created(){
            this.teachers=[
                {
                    TName:"林老师",
                    Position:"教研室主任",
                    Rank:"教授",
                    Phone:"12345678900",
                    Email:"123456@qq.com",
                    TopicDemand:"外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
                }
            ]
        }
    }
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