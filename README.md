# curriculum-design-2020

2020 大二下学期小型软件开发实践（课程设计）-- 毕业设计选题系统的设计与实现

采用前后端分离开发方式

前端
---

**UI 库：iView**

**前端框架：Vuejs ==> Vue-CLI**

**HTTP 库：Axiso.js**

后端
---

**后端开发语言：Java、Python**

**后端 Web 框架：Spring Boot、Flask**

**数据库框架：MyBatsi-Plus、Spring-Data-Redis**

**数据库：MySQL8.0、Redis3.2**

项目运行
---

### 前端运行

**确保当电脑有安装 Node，命令行可以执行 npm 命令**

在 project-webUI 目录下，进入命令行运行 `npm install` 命令安装依赖

安装完用 `npm run serve` 命令启动前端项目

### 后端

将 project-service-java 目录下的 spring boot 项目导入到 IDEA，然后创建 `application-dev.properties` 配置文件，配置好数据库

### 导入数据库

请不要使用 `毕业设计选题系统.sql` 这个文件导入数据库，因为他是数据库设计修改后的，但后端接口没有做相对应的修改

请使用 `runable.sql` 这个文件导入数据库（直接创建整个数据库，包括数据表以及一些预存的数据）
