<template>
    <div class="wrap">
      <div span="8" v-for="topic in topics" :key="topic.TopicName" class="cardWrap">
        <Card class="card">
          <p slot="title" class="bold" style="color:#2b85e4">{{topic.TopicName}}<Icon type="ios-paper" size="20" style="margin-left:20px"/></p>
          <Row class="row left">
            <Col span="2" class="bold">导师:</Col>
            <Col span="22">{{topic.TName}}</Col>
          </Row>
          <Row class="row left">
            <Col span="2" class="bold">类型:</Col>
            <Col span="22">{{topic.Type}}</Col>
          </Row>
          <Row class="row left">
            <Col span="2" class="bold">介绍:</Col>
            <Col span="22">{{topic.Introduction}}</Col>
          </Row>
        </Card>
      </div>
  </div>
</template>

<script>
    export default {
        name: 'AllTopic',
        data() {
            return {
                topics: []
            }
        },
        created(){
            this.topics = [
                {
                    TopicName: "毕业设计选题系统",
                    Type: "应用（实验）研究类",
                    TName: "老师一",
                    Introduction: "外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
                },
                {
                    TopicName: "毕业设计选题系统",
                    Type: "应用（实验）研究类",
                    TName: "老师一",
                    Introduction: "外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
                },
                {
                    TopicName: "毕业设计选题系统",
                    Type: "应用（实验）研究类",
                    TName: "老师一",
                    Introduction: "外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
                },
                {
                    TopicName: "毕业设计选题系统",
                    Type: "应用（实验）研究类",
                    TName: "老师一",
                    Introduction: "外模式(External Schema): 处于用户级、三级模式结构的最外层, 又称为子模式(External Schema)或用户模式(Subschema), 能被数据库用户看到并使用的那部分数据的逻辑结构和特征的描述, 也是数据库用户的数据视图(用户视图)。外模式是逻辑模式的某一部分的抽象表示, 一个数据库可以有多个外模式, 不同用户对应的外模式的描述可能不同, 同一外模式也可以被多个应用系统使用。"
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