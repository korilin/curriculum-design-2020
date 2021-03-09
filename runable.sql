CREATE DATABASE  IF NOT EXISTS `curriculum_design2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `curriculum_design2`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: curriculum_design2
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `AdminID` char(12) NOT NULL,
  `Password` varchar(30) NOT NULL DEFAULT '123456',
  PRIMARY KEY (`AdminID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员登陆表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','jiebin22');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `SID` char(12) NOT NULL,
  `TopicID` char(12) NOT NULL,
  `ApplyTime` datetime NOT NULL,
  `Status` enum('0','1','2','3','4') NOT NULL DEFAULT '0',
  PRIMARY KEY (`SID`,`TopicID`),
  KEY `apply_topic_idx` (`TopicID`),
  CONSTRAINT `apply_student` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`),
  CONSTRAINT `apply_topic` FOREIGN KEY (`TopicID`) REFERENCES `topic_info` (`TopicID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课题申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `DeptID` char(12) NOT NULL,
  `DeptName` varchar(20) NOT NULL,
  PRIMARY KEY (`DeptID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='院系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('100','城建与环境学院'),('105','金融与贸易学院'),('110','计算机与信息学院'),('115','文学与传媒学院'),('120','艺术学院'),('125','商学院'),('130','法学院'),('135','智能制造学院'),('140','外国语学院'),('145','创意设计学院'),('155','创新创业学院'),('160','体育教学部'),('165','马克思主义学院');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `PositionID` int NOT NULL,
  `PositionName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`PositionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profession`
--

DROP TABLE IF EXISTS `profession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profession` (
  `ProfID` char(12) NOT NULL,
  `ProfName` varchar(20) NOT NULL,
  `DeptID` char(12) NOT NULL,
  PRIMARY KEY (`ProfID`),
  KEY `profession_department_idx` (`DeptID`),
  CONSTRAINT `profession_department` FOREIGN KEY (`DeptID`) REFERENCES `department` (`DeptID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专业表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profession`
--

LOCK TABLES `profession` WRITE;
/*!40000 ALTER TABLE `profession` DISABLE KEYS */;
INSERT INTO `profession` VALUES ('10011','土木工程','100'),('10012','工程管理','100'),('10013','安全工程','100'),('10014','环境工程','100'),('10015','人文地理与城乡规划','100'),('10016','自然地理与资源环境','100'),('10511','国际经济与贸易','105'),('10512','保险学','105'),('10513','投资学','105'),('10514','金融学','105'),('10515','互联网金融','105'),('10516','电子商务','105'),('11011','计算机科学与技术','110'),('11012','软件工程','110'),('11013','建筑电气与智能化','110'),('11511','汉语言文学','115'),('11512','网络与新媒体','115'),('11513','秘书学','115'),('12011','音乐表演','120'),('12012','舞蹈编导','120'),('12013','表演','120'),('12511','会计学','125'),('12512','财务管理','125'),('12513','工商管理','125'),('12514','人力资源管理','125'),('12515','物流管理','125'),('12516','市场营销','125'),('13011','法学','130'),('13012','行政管理','130'),('13013','社会工作','130'),('13511','机械设计制造及其自动化','135'),('13512','机械电子工程','135'),('13513','材料成型及控制工程','135'),('13514','电子信息工程','135'),('13515','物联网工程','135'),('13516','印刷工程','135'),('14000','英语专业','140'),('14511','数字媒体艺术专业','145'),('14512','视觉传达专业','145'),('14513','工业设计专业','145');
/*!40000 ALTER TABLE `profession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rank` (
  `RankID` int NOT NULL,
  `RankName` varchar(10) NOT NULL,
  PRIMARY KEY (`RankID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `SID` char(12) NOT NULL,
  `SName` varchar(10) NOT NULL,
  `Grade` year NOT NULL,
  `ProfID` char(12) NOT NULL,
  `ClassNumber` int NOT NULL,
  `Password` varchar(30) NOT NULL DEFAULT '123456',
  PRIMARY KEY (`SID`),
  KEY `student_profession_idx` (`ProfID`),
  CONSTRAINT `student_profession` FOREIGN KEY (`ProfID`) REFERENCES `profession` (`ProfID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('123456789999','xcs',2020,'11012',4,'123456'),('201835000000','test',2018,'10511',6,'123456'),('201835020801','测试1',2018,'11012',8,'123456'),('201835020802','测试2',2018,'11012',8,'123456'),('201835020803','测试3',2018,'11012',8,'123456'),('201835020804','测试4',2018,'11012',8,'123456'),('201835020805','测试5',2018,'11012',8,'123456'),('201835020822','林洁彬',2018,'11012',8,'123456'),('201835020832','谢铭轩',2018,'11012',8,'123456'),('201835020899','测试',2020,'12512',4,'123456');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `TID` char(12) NOT NULL,
  `TName` varchar(10) NOT NULL,
  `PositionID` int DEFAULT NULL,
  `RankID` int DEFAULT NULL,
  `GuideProfID` char(12) NOT NULL,
  `Phone` char(11) DEFAULT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `TopicDemand` varchar(800) NOT NULL DEFAULT '无',
  `Password` varchar(45) NOT NULL DEFAULT '123456',
  `Position` varchar(45) DEFAULT NULL,
  `TRank` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TID`),
  KEY `guide_proffesion_idx` (`GuideProfID`),
  KEY `rank_rk_idx` (`RankID`),
  KEY `position_fk_idx` (`PositionID`),
  CONSTRAINT `guide_proffesion` FOREIGN KEY (`GuideProfID`) REFERENCES `profession` (`ProfID`),
  CONSTRAINT `position_fk` FOREIGN KEY (`PositionID`) REFERENCES `position` (`PositionID`),
  CONSTRAINT `rank_fk` FOREIGN KEY (`RankID`) REFERENCES `rank` (`RankID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导师表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('123456','导师名称',NULL,NULL,'10013','无','无','无','123456','教师','讲师');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_info`
--

DROP TABLE IF EXISTS `topic_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_info` (
  `TopicID` char(12) NOT NULL,
  `TopicName` varchar(30) NOT NULL,
  `Introduction` varchar(800) NOT NULL DEFAULT '无介绍',
  `TID` char(12) NOT NULL,
  `TypeID` int NOT NULL,
  `Source` enum('0','1') NOT NULL,
  `SID` char(12) DEFAULT NULL,
  PRIMARY KEY (`TopicID`),
  KEY `topic_teacher_idx` (`TID`),
  KEY `topic_type_idx` (`TypeID`),
  KEY `topic_student_idx` (`SID`),
  CONSTRAINT `topic_student` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`),
  CONSTRAINT `topic_teacher` FOREIGN KEY (`TID`) REFERENCES `teacher` (`TID`),
  CONSTRAINT `topic_type` FOREIGN KEY (`TypeID`) REFERENCES `topic_type` (`TypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课题信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_info`
--

LOCK TABLES `topic_info` WRITE;
/*!40000 ALTER TABLE `topic_info` DISABLE KEYS */;
INSERT INTO `topic_info` VALUES ('161529278494','课题1','课题添加测试','123456',1004,'0',NULL),('161529279892','课题2','欸嘿嘿','123456',1002,'0',NULL);
/*!40000 ALTER TABLE `topic_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_type`
--

DROP TABLE IF EXISTS `topic_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_type` (
  `TypeID` int NOT NULL AUTO_INCREMENT,
  `TypeName` varchar(20) NOT NULL,
  PRIMARY KEY (`TypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COMMENT='课题类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_type`
--

LOCK TABLES `topic_type` WRITE;
/*!40000 ALTER TABLE `topic_type` DISABLE KEYS */;
INSERT INTO `topic_type` VALUES (1000,'其它'),(1001,'工程设计类'),(1002,'理论研究类'),(1003,'应用（实验）研究类'),(1004,'软件设计类');
/*!40000 ALTER TABLE `topic_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'curriculum_design2'
--

--
-- Dumping routines for database 'curriculum_design2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-09 20:40:20
