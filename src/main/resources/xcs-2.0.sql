-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: xcs
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boolean_history`
--

DROP TABLE IF EXISTS `boolean_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boolean_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` bit(1) DEFAULT NULL,
  `x_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `xItem_time` (`x_item_id`,`time`),
  CONSTRAINT `FKq6osgvtgx4mbh3xliq1cgl4vy` FOREIGN KEY (`x_item_id`) REFERENCES `xitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `double_history`
--

DROP TABLE IF EXISTS `double_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `double_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` double DEFAULT NULL,
  `x_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `xItem_time` (`x_item_id`,`time`),
  CONSTRAINT `FK6pexsr371xy0jddh33y3ftn13` FOREIGN KEY (`x_item_id`) REFERENCES `xitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `file_detail`
--

DROP TABLE IF EXISTS `file_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `extension` varchar(8) DEFAULT NULL,
  `family` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `integer_history`
--

DROP TABLE IF EXISTS `integer_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `integer_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `x_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `xItem_time` (`x_item_id`,`time`),
  CONSTRAINT `FK49yl9wwv9dihp4qcp1q3odnmg` FOREIGN KEY (`x_item_id`) REFERENCES `xitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(64) DEFAULT NULL,
  `permission_family_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7wwn47u5ghe70kookl1620qg1` (`permission_family_id`),
  CONSTRAINT `FK7wwn47u5ghe70kookl1620qg1` FOREIGN KEY (`permission_family_id`) REFERENCES `permission_family` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'管理OPC','/center/manage_opc',1),(2,'管理GROUP','/center/manage_group',1),(3,'管理ITEM','/center/manage_item',1),(4,'系统设置','/center/settings',1),(5,'绘制组态图形','/center/cs_paint',1),(6,'管理用户','/center/user',1),(7,'管理用户组','/center/user_group',1),(8,'管理权限','/center/permission',1),(9,'启动OPC','/opcClientStart',2),(10,'关闭OPC','/opcClientStop',2),(11,'初始化OPC数据','/initopcs',2),(12,'批量保存OPC','/manage/saveopcs',2),(13,'保存OPC','/manage/saveopc',2),(14,'批量删除OPC','/manage/deleteopcs',2),(15,'分页获取OPC数据','/manage/getopcsbypage',2),(16,'获取OPC数据','/manage/getopcs',2),(17,'批量保存GROUP','/manage/savegroups',2),(18,'保存GROUP','/manage/savegroup',2),(19,'批量删除GROUP','/manage/deletegroups',2),(20,'通过opcId,groupName分页查询GROUP','/manage/getgroupsbypage',2),(21,'分页获取GROUP数据','/manage/getallgroupsbypage',2),(22,'获取所有GROUP数据','/manage/getgroups',2),(23,'批量保存ITEM','/manage/saveitems',2),(24,'保存ITEM','/manage/saveitem',2),(25,'批量删除ITEM','/manage/deleteitems',2),(26,'通过groupId,itemName分页查询ITEM','/manage/getitemsbypage',2),(27,'分页获取ITEM','/manage/getallitemsbypage',2),(28,'通过itemId查询ITEM','/manage/getitem',2),(29,'查询某一ITEM起始时间历史数据','/manage/gethistory',2),(30,'获取所有权限分类','/permission/findallpermissionfamily',3),(31,'添加权限分类','/permission/addpermissionfamily',3),(32,'删除权限分类','/permission/deletepermissionfamily',3),(33,'通过权限分类获取权限','/permission/findpermissionbyfamily',3),(34,'添加权限','/permission/addpermission',3),(35,'删除权限','/permission/deletepermission',3),(36,'通过名字或电话获取用户','/user/findusersbynameortel_page',4),(37,'保存用户','/user/saveuser',4),(38,'删除用户','/user/deleteuser',4),(39,'获取用户组','/user/findallusergroups',4),(40,'通过名字获取用户组','/user/findusergroupsbyname',4),(41,'保存用户组','/user/saveusergroup',4),(42,'删除用户组','/user/deleteusergroup',4),(43,'获取用户组拥有的权限','/user/findpermissionsbyusergroup',4),(44,'获取设置','/settings/get',5),(45,'保存设置','/settings/save',5),(47,'上传','/upload',6),(48,'获取所有文件信息','/files/all',6),(51,'删除组态文件','/files/delete',6),(54,'上传文件','/center/file_upload',1),(55,'管理文件','/center/manage_file',1),(56,'获取文件内容','/files/content',6),(57,'搜索','/files/search',6),(58,'保存更新','/files/save',6);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_family`
--

DROP TABLE IF EXISTS `permission_family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_family` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` varchar(128) DEFAULT NULL,
  `name` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_family`
--

LOCK TABLES `permission_family` WRITE;
/*!40000 ALTER TABLE `permission_family` DISABLE KEYS */;
INSERT INTO `permission_family` VALUES (1,'访问页面','访问页面'),(2,'管理OPC','管理OPC'),(3,'管理Permission','管理Permission'),(4,'管理User','管理User'),(5,'系统设置','系统设置'),(6,'组态文件','组态文件');
/*!40000 ALTER TABLE `permission_family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('enable_save_data','false'),('push_data_interval','3000'),('save_data_interval','3000');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `string_history`
--

DROP TABLE IF EXISTS `string_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `string_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` varchar(128) DEFAULT NULL,
  `x_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `xItem_time` (`x_item_id`,`time`),
  CONSTRAINT `FKj2p5lnt1ycpct9qjx45xjkq31` FOREIGN KEY (`x_item_id`) REFERENCES `xitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `tel` varchar(11) NOT NULL,
  `user_group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nbfia2ok6c7at4i0er6uyskkx` (`tel`),
  KEY `FKd5uhmsqhax1l70pck9lmgphjr` (`user_group_id`),
  CONSTRAINT `FKd5uhmsqhax1l70pck9lmgphjr` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'njdx','admin','158518',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,'超级管理员'),(2,'OPC管理员'),(3,'权限管理员'),(4,'用户管理员'),(5,'普通用户');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_permissions`
--

DROP TABLE IF EXISTS `user_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group_permissions` (
  `user_groups_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  PRIMARY KEY (`user_groups_id`,`permissions_id`),
  KEY `FK1kp2p8of20958qgwsh077f38q` (`permissions_id`),
  CONSTRAINT `FK1kp2p8of20958qgwsh077f38q` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKr42ybyo54h4xtr6lsgk7uqjm0` FOREIGN KEY (`user_groups_id`) REFERENCES `user_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_permissions`
--

LOCK TABLES `user_group_permissions` WRITE;
/*!40000 ALTER TABLE `user_group_permissions` DISABLE KEYS */;
INSERT INTO `user_group_permissions` VALUES (1,1),(2,1),(1,2),(2,2),(1,3),(2,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(2,12),(1,13),(2,13),(1,14),(2,14),(1,15),(2,15),(1,16),(2,16),(1,17),(2,17),(1,18),(2,18),(1,19),(2,19),(1,20),(2,20),(1,21),(2,21),(1,22),(2,22),(1,23),(2,23),(1,24),(2,24),(1,25),(2,25),(1,26),(2,26),(1,27),(2,27),(1,28),(2,28),(1,29),(2,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,47),(1,48),(1,51),(1,54),(1,55),(1,56),(1,57),(1,58);
/*!40000 ALTER TABLE `user_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xgroup`
--

DROP TABLE IF EXISTS `xgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `group_name` varchar(32) NOT NULL,
  `percent_dead_band` float NOT NULL,
  `update_rate` int(11) NOT NULL,
  `opc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm0o99ykm4qkrs1aknkoukaehd` (`opc_id`),
  CONSTRAINT `FKm0o99ykm4qkrs1aknkoukaehd` FOREIGN KEY (`opc_id`) REFERENCES `xopc` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xgroup`
--

LOCK TABLES `xgroup` WRITE;
/*!40000 ALTER TABLE `xgroup` DISABLE KEYS */;
INSERT INTO `xgroup` VALUES (1,'','Random',0,1000,1),(2,'','Triangle',0,1000,1),(3,'','Square',0,1000,1);
/*!40000 ALTER TABLE `xgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xitem`
--

DROP TABLE IF EXISTS `xitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_path` varchar(16) NOT NULL,
  `active` bit(1) NOT NULL,
  `item_name` varchar(32) NOT NULL,
  `max` double DEFAULT NULL,
  `min` double DEFAULT NULL,
  `normal` double DEFAULT NULL,
  `type` varchar(8) NOT NULL,
  `unit` varchar(16) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `notes` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKodp0idw6dvsfmbrccg4esjawj` (`group_id`),
  CONSTRAINT `FKodp0idw6dvsfmbrccg4esjawj` FOREIGN KEY (`group_id`) REFERENCES `xgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xitem`
--

LOCK TABLES `xitem` WRITE;
/*!40000 ALTER TABLE `xitem` DISABLE KEYS */;
INSERT INTO `xitem` VALUES (1,'','','Random.Int1',100,0,66,'INTEGER','kg/m3',1,''),(2,'','','Random.Int2',NULL,NULL,NULL,'INTEGER',NULL,1,NULL),(3,'','','Random.Real4',NULL,NULL,NULL,'DOUBLE',NULL,1,NULL),(4,'','','Random.String',NULL,NULL,NULL,'STRING',NULL,1,NULL),(5,'','','Random.Time',NULL,NULL,NULL,'STRING',NULL,1,NULL),(6,'','','Triangle Waves.Int1',NULL,NULL,NULL,'INTEGER',NULL,2,NULL),(7,'','','Triangle Waves.Int2',NULL,NULL,NULL,'INTEGER',NULL,2,NULL),(8,'','','Triangle Waves.Int4',NULL,NULL,NULL,'INTEGER',NULL,2,NULL),(9,'','','Triangle Waves.Real4',NULL,NULL,NULL,'DOUBLE',NULL,2,NULL),(10,'','','Triangle Waves.Real8',NULL,NULL,NULL,'DOUBLE',NULL,2,NULL),(11,'','','Square Waves.Boolean',NULL,NULL,NULL,'BOOLEAN',NULL,3,NULL),(12,'','','Square Waves.Int1',NULL,NULL,NULL,'INTEGER',NULL,3,NULL),(13,'','','Square Waves.Int2',NULL,NULL,NULL,'INTEGER',NULL,3,NULL),(14,'','','Square Waves.Int4',NULL,NULL,NULL,'INTEGER',NULL,3,NULL),(15,'','','Square Waves.Real8',NULL,NULL,NULL,'DOUBLE',NULL,3,NULL);
/*!40000 ALTER TABLE `xitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xopc`
--

DROP TABLE IF EXISTS `xopc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xopc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(64) NOT NULL,
  `server_client_handle` varchar(16) NOT NULL,
  `server_progid` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xopc`
--

LOCK TABLES `xopc` WRITE;
/*!40000 ALTER TABLE `xopc` DISABLE KEYS */;
INSERT INTO `xopc` VALUES (1,'localhost','JOPC1','Matrikon.OPC.Simulation');
/*!40000 ALTER TABLE `xopc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-14 14:13:42
