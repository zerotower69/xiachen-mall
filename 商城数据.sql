/*
MySQL Backup
Database: mymall
Backup Time: 2020-10-05 16:46:05
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `mymall`.`cart`;
DROP TABLE IF EXISTS `mymall`.`category`;
DROP TABLE IF EXISTS `mymall`.`member`;
DROP TABLE IF EXISTS `mymall`.`myorder`;
DROP TABLE IF EXISTS `mymall`.`product`;
DROP TABLE IF EXISTS `mymall`.`task`;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车自增id',
  `user_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户的名字',
  `user_id` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户的id',
  `product_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品的名字',
  `product_code` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品对应的独一无二的编码',
  `product_category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品的所属分类',
  `each_price` decimal(10,2) DEFAULT NULL COMMENT '单件的价格',
  `product_num` int DEFAULT NULL COMMENT '购买的数量',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '总价',
  `active` int NOT NULL DEFAULT '0' COMMENT '订单激活态，判断商品属于购物车还是订单，0  购物车；1 订单',
  `order_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '00000000000000000000' COMMENT '订单代码',
  `create_time` datetime DEFAULT NULL COMMENT '所属订单的创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `category` (
  `cat_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类自增id',
  `name` char(50) DEFAULT NULL COMMENT '分类名',
  `parent_id` bigint DEFAULT NULL COMMENT '父分类的id',
  `cat_level` int DEFAULT NULL COMMENT '分类层级，目前只有1 2 3 ',
  `sort` int DEFAULT NULL COMMENT '排序',
  `product_count` int DEFAULT NULL COMMENT '此分类下具有的商品总数',
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户自增id',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名，最大长度不得超过20，且其具有唯一性',
  `user_id` char(18) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户账号id,唯一身份标识符,前八位是创建账号的日期，后四位是随机数',
  `password` char(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码最大长度不得超过16位,且必须包含大小写字母、数字中的两种',
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户的邮箱，用于验证信息',
  `status` int DEFAULT '0' COMMENT '账号状态,0是未登录，1是登录状态，2是异常，3是账号违规中',
  `active` int DEFAULT NULL COMMENT '账号激活态，0是注册未激活，1是已经激活',
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户头像的url',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_user_name_uindex` (`user_name`),
  UNIQUE KEY `member_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `myorder` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单自增id',
  `order_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '订单编号年月日小时加上0000统计 小时最大订单数9999',
  `user_name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建订单的用户名',
  `user_id` varchar(18) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建订单的用户id',
  `order_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单的内容',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建的时间',
  `count` int DEFAULT '0' COMMENT '订单的总商品数',
  `amount` decimal(11,2) DEFAULT '0.00' COMMENT '订单的总额',
  `status` int DEFAULT '0' COMMENT '订单状态 0--下单  1--取消  2--发货  3--运送中 4-- 已签收',
  `send_addr` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单发送的邮箱地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品自增id',
  `product_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `product_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品信息',
  `product_code` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品编码,前2位表示一级分类的代码，3-5表示二级分类代码，6-8三级分类，9-12随机数产生，但是不能重复，否则重新生成',
  `product_num` int NOT NULL COMMENT '商品库存',
  `product_category` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品所属的三级分类',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `product_unit` char(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品计量单位',
  `img_addr` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品图片的保存地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_product_code_uindex` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商品详细信息';
CREATE TABLE `task` (
  `task_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `title` varchar(12) NOT NULL COMMENT '任务标题,不能重复',
  `code` varchar(20) NOT NULL COMMENT '任务编号，前4位固定8032代表任务编号,5-12位代表日期,13-14位代表创建小时数,15-17位序号排序',
  `content` varchar(255) NOT NULL COMMENT '任务内容',
  `create_time` datetime NOT NULL COMMENT '创建任务时间',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始计时时间',
  `predict_time` datetime DEFAULT NULL COMMENT '任务预计完成时间',
  `real_time` datetime DEFAULT NULL COMMENT '任务实际完成时间',
  `use_time` bigint NOT NULL DEFAULT '0' COMMENT '总用时，记录的是秒数',
  `status` int NOT NULL DEFAULT '0' COMMENT '任务状态：0--创建  1--正在编辑  2--正在计时  3--暂停计时\n4--已经完成',
  `history` tinyint(1) NOT NULL DEFAULT '0' COMMENT '历史状态',
  `info` varchar(300) DEFAULT NULL COMMENT '详细备注信息',
  `overtime` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否超时',
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `title` (`title`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务状态表';
BEGIN;
LOCK TABLES `mymall`.`cart` WRITE;
DELETE FROM `mymall`.`cart`;
INSERT INTO `mymall`.`cart` (`id`,`user_name`,`user_id`,`product_name`,`product_code`,`product_category`,`each_price`,`product_num`,`amount`,`active`,`order_code`,`create_time`) VALUES (2, 'test1', '103202008260139765', '裙子', '165273682341', '女装', 199.00, 12, 2388.00, 1, '01397620200917722000', '2020-09-17 15:23:08'),(3, 'test1', '103202008260139765', '达利园小面包', '123456576778', '蛋糕', 5.00, 11, 55.00, 1, '01397620200917531926', '2020-09-17 15:21:56'),(4, 'test2', '999998', '苹果手机', '346576715434', '手机', 4700.00, 10, 47000.00, 0, '', NULL),(5, 'test3', '999993', '久量台灯', '852525205394', '台灯', 52.00, 10, 520.00, 0, '', NULL),(6, 'test1', '103202008260139765', '酱油', '345676788799', '调味用品', 13.50, 16, 216.00, 1, '013976202009172058', '2020-09-17 12:39:18'),(7, 'test2', '999998', '裙子', '165273682341', '女装', 199.00, 2, 398.00, 0, '00000000000000000000', NULL),(9, 'test1', '103202008260139765', '《挪威的森林》', '3456678', '当代文学', 34.78, 12, 382.58, 1, '013976202009172058', '2020-09-17 12:39:18'),(10, 'test99', '202009100012', '《挪威的森林》', '3456678', '当代文学', 34.78, 2, 69.56, 0, '', NULL),(11, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5899.00, 3, 17697.00, 1, '01397620200917118471', '2020-09-17 15:05:02'),(13, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5899.00, 2, 11798.00, 1, '01397620200918755023', '2020-09-17 16:11:06'),(16, 'test1', '103202008260139765', '长条面包', '8888888', '面包', 5.00, 1, 5.00, 1, '0139762020091844847', '2020-09-17 16:16:24'),(18, 'test1', '103202008260139765', '《绿色的回忆》', '7364374', '青春小说', 23.12, 1, 23.12, 1, '01397620200918831165', '2020-09-18 00:27:22'),(19, 'test1', '103202008260139765', '《挪威的森林》', '3456678', '当代文学', 34.78, 6, 208.68, 1, '01397620200918831165', '2020-09-18 00:27:22'),(20, 'test1', '103202008260139765', '达利园小面包', '3446576', '面包', 12.00, 7, 84.00, 1, '01397620200918831165', '2020-09-18 00:27:22'),(21, 'test1', '103202008260139765', '福临门虎纹面包', '9876567', '面包', 4.50, 1, 4.50, 1, '01397620200918831165', '2020-09-18 00:27:22'),(22, 'test1', '103202008260139765', '长条面包', '8888888', '面包', 5.00, 1, 5.00, 1, '01397620200918831165', '2020-09-18 00:27:22'),(23, 'zerotower', '103202009180836591', '华为P40', '8912675', '华为手机', 5899.00, 43, 253657.00, 1, '08365920200918374549', '2020-09-18 00:30:42'),(24, 'zerotower', '103202009180836591', '华为nova3', '3445543', '华为手机', 3899.00, 10, 3899.00, 0, '0', NULL),(25, 'test1', '103202008260139765', '《挪威的森林》', '3456678', '当代文学', 34.78, 1, 34.78, 1, '01397620200918898899', '2020-09-18 00:47:48'),(26, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5899.00, 1, 5899.00, 1, '01397620200920313055', '2020-09-20 10:14:05'),(27, 'test1', '103202008260139765', '华为nova3', '3445543', '华为手机', 3899.00, 1, 3899.00, 1, '01397620200926842746', '2020-09-26 14:40:46'),(28, 'test1', '103202008260139765', '达利园小面包', '3446576', '面包', 12.00, 1, 12.00, 1, '01397620200923454533', '2020-09-23 08:39:38'),(29, 'test1', '103202008260139765', '福临门虎纹面包', '9876567', '面包', 4.50, 1, 4.50, 1, '01397620200926842746', '2020-09-26 14:40:46'),(30, 'test111111', '103202009242186300', '达利园小面包', '3446576', '面包', 12.00, 2, 12.00, 1, '2186302020092484090', '2020-09-24 13:14:55'),(31, 'test111111', '103202009242186300', '福临门虎纹面包', '9876567', '面包', 4.50, 1, 4.50, 1, '2186302020092484090', '2020-09-24 13:14:55'),(32, 'test111111', '103202009242186300', '长条面包', '8888888', '面包', 5.00, 1, 5.00, 1, '2186302020092484090', '2020-09-24 13:14:55'),(33, '哈哈哈', '103202009262257948', '《堂吉诃德》', '12987122', '测试', 66.66, 5, 66.66, 0, '0', NULL),(34, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 2, 66.66, 1, '01397620200929632332', NULL),(37, 'test1', '103202008260139765', '汤达人·日式豚骨', '3874837', '方便面', 5.21, 3, 5.21, 1, '01397620200929768342', '2020-09-29 05:07:23'),(38, 'test1', '103202008260139765', '汤达人·日式豚骨', '3874837', '方便面', 5.21, 4, 5.21, 1, '01397620200929703370', '2020-09-29 05:15:14'),(39, 'test1', '103202008260139765', '渔夫帽', '3423333', '帽子(男）', 67.23, 1, 67.23, 1, '01397620200929458608', '2020-09-29 05:15:21'),(40, 'test1', '103202008260139765', '汤达人·日式豚骨', '3874837', '方便面', 5.21, 1, 5.21, 1, '01397620200929607906', '2020-09-29 05:18:11'),(41, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '0139762020092961437', '2020-09-29 05:28:55'),(42, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5000.00, 1, 5000.00, 1, '01397620200929510250', '2020-09-29 05:32:56'),(43, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5000.00, 1, 5000.00, 1, '01397620200929645998', '2020-09-29 05:36:11'),(44, 'test1', '103202008260139765', '渔夫帽', '3423333', '帽子(男）', 67.23, 1, 67.23, 1, '01397620200929816773', '2020-09-29 05:38:38'),(45, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200929477147', '2020-09-29 05:40:17'),(46, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200929139821', '2020-09-29 05:42:07'),(47, 'test1', '103202008260139765', '达利园小面包', '3446576', '面包', 12.00, 3, 12.00, 1, '01397620200929581673', '2020-09-29 05:48:37'),(48, 'test1', '103202008260139765', '渔夫帽', '3423333', '帽子(男）', 67.23, 1, 67.23, 1, '0139762020092922679', '2020-09-29 05:53:07'),(49, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200929947757', '2020-09-29 06:15:57'),(50, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5000.00, 1, 5000.00, 1, '0139762020092962552', '2020-09-29 06:16:00'),(51, 'test1', '103202008260139765', 'iphoneX', '3445666', '苹果手机', 6899.00, 1, 6899.00, 1, '01397620200929461754', '2020-09-29 06:16:02'),(52, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200929399165', '2020-09-29 06:28:01'),(54, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5000.00, 2, 5000.00, 1, '01397620200929357423', '2020-09-29 06:38:05'),(58, 'test1', '103202008260139765', '达利园小面包', '3446576', '面包', 12.00, 5, 60.00, 1, '01397620200929129931', '2020-09-29 07:43:17'),(60, 'test1', '103202008260139765', '长条面包', '8888888', '面包', 5.00, 1, 5.00, 1, '01397620200929660924', '2020-09-29 07:43:21'),(61, '我是谁', '103202009291022887', '渔夫帽', '3423333', '帽子(男）', 67.23, 1, 67.23, 1, '10228820200929911893', '2020-09-29 10:11:25'),(62, '我是谁', '103202009291022887', '福临门虎纹面包', '9876567', '面包', 4.50, 1, 4.50, 1, '10228820200929911893', '2020-09-29 10:11:30'),(63, '我是谁', '103202009291022887', '汤达人·日式豚骨', '3874837', '方便面', 5.21, 1, 5.21, 1, '10228820200929911893', '2020-09-29 10:11:33'),(64, 'test1', '103202008260139765', '《java·21days》', '3434322', '程序设计', 34.56, 1, 34.56, 1, '01397620200929466629', '2020-09-29 12:55:44'),(65, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 5000.00, 1, 5000.00, 1, '01397620200929466629', '2020-09-29 12:55:50'),(67, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 2, 133.32, 1, '01397620200929615599', '2020-09-29 14:24:22'),(68, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200929242261', '2020-09-29 14:57:09'),(71, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 4, 66.66, 0, '01397620200929207049', '2020-09-29 15:51:33'),(72, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '01397620200930555285', NULL),(73, 'test1', '103202008260139765', '《堂吉诃德》', '12987122', '测试', 66.66, 1, 66.66, 1, '0139762020093028494', NULL),(74, 'test1', '103202008260139765', '华为P40', '8912675', '华为手机', 6000.00, 5, 30000.00, 1, '01397620200930688996', NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `mymall`.`category` WRITE;
DELETE FROM `mymall`.`category`;
INSERT INTO `mymall`.`category` (`cat_id`,`name`,`parent_id`,`cat_level`,`sort`,`product_count`) VALUES (1, '图书、音像', 0, 1, 0, NULL),(2, '食品', 0, 1, 0, NULL),(3, '电子产品', 0, 1, 0, NULL),(4, '服饰', 0, 1, 0, NULL),(5, '医疗保健', 0, 1, 0, NULL),(6, '纸质书', 1, 2, 0, NULL),(7, '电子书', 1, 2, 0, NULL),(8, '音像制品', 1, 2, 0, NULL),(9, '网络文学', 6, 3, 0, NULL),(10, '青春小说', 6, 3, 0, NULL),(11, '当代文学', 6, 3, 0, NULL),(12, '网络小说', 7, 3, 0, NULL),(13, '当代文学', 7, 3, 0, NULL),(14, '音乐专辑', 8, 3, 0, NULL),(15, '水果', 2, 2, 0, NULL),(16, '蔬菜', 2, 2, 0, NULL),(17, '干粮', 2, 2, 0, NULL),(18, '面包', 17, 3, 0, NULL),(19, '方便面', 17, 3, 0, NULL),(20, '手机', 3, 2, 0, NULL),(21, '华为手机', 20, 3, 0, NULL),(22, '苹果手机', 20, 3, 0, NULL),(23, '男装', 4, 2, 0, NULL),(24, '女装', 4, 2, 0, NULL),(25, '帽子(男）', 23, 3, 0, NULL),(26, '长袖（男）', 23, 3, 0, NULL),(27, '长裤（男）', 23, 3, 0, NULL),(28, '程序设计', 6, 3, NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `mymall`.`member` WRITE;
DELETE FROM `mymall`.`member`;
INSERT INTO `mymall`.`member` (`id`,`user_name`,`user_id`,`password`,`user_email`,`status`,`active`,`avatar`) VALUES (1, 'test1', '103202008260139765', 'hl123456', '1761552891@qq.com', 0, 1, NULL),(3, 'test2', '103202008261097542', 'hl123456', NULL, 0, 0, NULL),(4, 'test99', '103202009100012863', 'hl123456', '16619728657@163.com', 0, 0, NULL),(6, 'test77', '103202009132093716', 'hl123456', '176@qq.com', 0, 1, NULL),(7, 'test66', '103202009132014875', 'hl123456', '176@qq.com', 0, 1, NULL),(8, 'zerotower', '103202009180836591', 'hl,wdmall69', '1761552891@qq.com', 0, 1, NULL),(9, 'test1111', '103202009241898759', '123456', '1171004193@cnu.edu.cn', 0, 0, NULL),(10, 'test111111', '103202009242186300', '12345678', '1171004193@cnu.edu.cn', 0, 1, NULL),(11, 'cesium', '103202009250081003', '12345678', '1761552891@qq.com', 0, 1, NULL),(12, '测试11', '103202009250886527', '12345678', '1761552891@qq.com', 0, 1, NULL),(13, '哈哈哈', '103202009262257948', '12345678', '1761552891@qq.com', 0, 1, NULL),(14, '我是谁', '103202009291022887', '12345678', '1761552891@qq.com', 0, 1, NULL),(15, '测试aaa', '103202009292022561', '12345678', '1761552891@qq.com', 0, 0, NULL),(16, '星期二', '103202009292091138', '12345678', '1761552891@qq.com', 0, 1, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `mymall`.`myorder` WRITE;
DELETE FROM `mymall`.`myorder`;
INSERT INTO `mymall`.`myorder` (`id`,`order_code`,`user_name`,`user_id`,`order_info`,`create_time`,`count`,`amount`,`status`,`send_addr`) VALUES (9, '01397620200917118471', 'test1', '103202008260139765', NULL, '2020-09-17 15:05:02', 1, 5899.00, 0, '1761552891@qq.com'),(10, '01397620200917531926', 'test1', '103202008260139765', NULL, '2020-09-17 15:21:56', 1, 55.00, 0, '1761552891@qq.com'),(11, '01397620200917722000', 'test1', '103202008260139765', NULL, '2020-09-17 15:23:08', 1, 2388.00, 0, '1761552891@qq.com'),(12, '01397620200918755023', 'test1', '103202008260139765', NULL, '2020-09-17 16:11:06', 1, 5899.00, 0, '1761552891@qq.com'),(14, '01397620200918831165', 'test1', '103202008260139765', NULL, '2020-09-18 00:27:22', 5, 79.40, 0, '1761552891@qq.com'),(15, '08365920200918374549', 'zerotower', '103202009180836591', NULL, '2020-09-18 00:30:42', 1, 5899.00, 0, '1761552891@qq.com'),(16, '01397620200918898899', 'test1', '103202008260139765', NULL, '2020-09-18 00:47:48', 1, 34.78, 0, '1761552891@qq.com'),(17, '01397620200920313055', 'test1', '103202008260139765', NULL, '2020-09-20 10:14:05', 1, 5899.00, 0, '1761552891@qq.com'),(18, '01397620200923454533', 'test1', '103202008260139765', NULL, '2020-09-23 08:39:38', 1, 12.00, 0, '1761552891@qq.com'),(20, '01397620200926842746', 'test1', '103202008260139765', NULL, '2020-09-26 14:40:46', 2, 3903.50, 0, '1761552891@qq.com'),(21, '01397620200929138446', 'test1', '103202008260139765', NULL, '2020-09-29 06:28:09', 1, 66.66, 0, '1761552891@qq.com'),(22, '01397620200929407247', 'test1', '103202008260139765', NULL, '2020-09-29 06:40:01', 1, 5000.00, 0, '1761552891@qq.com'),(23, '01397620200929599279', 'test1', '103202008260139765', NULL, '2020-09-29 07:43:36', 1, 5.00, 0, '1761552891@qq.com'),(24, '10228820200929911893', '我是谁', '103202009291022887', NULL, '2020-09-29 10:11:49', 3, 76.94, 0, '1761552891@qq.com'),(25, '01397620200929129931', 'test1', '103202008260139765', NULL, '2020-09-29 12:48:18', 1, 12.00, 0, '1761552891@qq.com'),(26, '01397620200929466629', 'test1', '103202008260139765', NULL, '2020-09-29 12:56:45', 2, 5034.56, 0, '1761552891@qq.com'),(27, '01397620200929615599', 'test1', '103202008260139765', NULL, '2020-09-29 14:54:30', 1, 66.66, 0, '1761552891@qq.com'),(28, '01397620200929242261', 'test1', '103202008260139765', NULL, '2020-09-29 14:58:01', 1, 66.66, 0, '1761552891@qq.com'),(29, '01397620200930555285', 'test1', '103202008260139765', NULL, '2020-09-29 17:45:09', 1, 66.66, 0, '1761552891@qq.com'),(31, '01397620200930688996', 'test1', '103202008260139765', NULL, '2020-09-29 17:51:10', 1, 30000.00, 0, '1761552891@qq.com');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `mymall`.`product` WRITE;
DELETE FROM `mymall`.`product`;
INSERT INTO `mymall`.`product` (`id`,`product_name`,`product_info`,`product_code`,`product_num`,`product_category`,`product_price`,`product_unit`,`img_addr`) VALUES (1, '华为P40', '强大的麒麟芯片，徕米拍照功能', '8912675', 23, '华为手机', 5000.00, '台', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/p40.jpg'),(2, '《挪威的森林》', '作者：村上春树', '3456678', 13, '当代文学', 34.78, '本', NULL),(3, '《哈利波特与魔法石》', '作者：J.K 罗琳', '3454556', 12, '魔幻小说', 23.78, '本', NULL),(4, '达利园小面包', '蛋黄味，浓浓法式情。', '3446576', 2, '面包', 12.00, '包', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/daliyuan.jpg'),(5, '福临门虎纹面包', '吃几个，顶饱儿', '9876567', 5, '面包', 4.50, '个', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/redflag.jpg'),(6, '华为nova3', '强大的拍照手机，夜里你最美', '3445543', 8, '华为手机', 3899.00, '台', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/nova3.jpg'),(7, 'iphoneX', '流畅的IOS,拍照全新升级', '3445666', 34, '苹果手机', 6899.00, '台', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/iphoneX.jpg'),(8, '长条面包', '长长的面包', '8888888', 23, '面包', 5.00, '个', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/%E9%95%BF%E6%9D%A1%E9%9D%A2%E5%8C%85.jpg'),(12, '汤达人·日式豚骨', '全新口味，全新包装', '3874837', 45, '方便面', 5.21, '包', 'https://springztmall.oss-cn-beijing.aliyuncs.com/images/%E6%97%A5%E5%BC%8F%E8%B1%9A%E9%AA%A8.jpg'),(13, '《java·21days》', 'java快速入门', '3434322', 3, '程序设计', 34.56, '本', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3909007992,4223073026&fm=26&gp=0.jpghttps://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3909007992,4223073026&fm=26&gp=0.jpg'),(18, '《Java·面向对象高级教程》', NULL, '3412121', 6, '程序设计', 67.89, '本', NULL),(19, '渔夫帽', NULL, '3423112', 45, '帽子(男）', 34.12, '顶', NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `mymall`.`task` WRITE;
DELETE FROM `mymall`.`task`;
INSERT INTO `mymall`.`task` (`task_id`,`title`,`code`,`content`,`create_time`,`start_time`,`predict_time`,`real_time`,`use_time`,`status`,`history`,`info`,`overtime`) VALUES (1, '支付功能', '80322020100314001', '增加商品的支付功能', '2020-10-04 10:24:16', '2020-10-04 02:54:45', '2020-10-06 14:25:05', NULL, 3, 2, 0, NULL, 0),(2, '测hi', '80322020100401001', '测试', '2020-10-04 01:59:42', '2020-10-04 02:12:29', '2020-10-17 00:00:00', NULL, 15, 2, 0, NULL, 0),(3, '制作时间组件', '80322020100404001', '时间统计状态和实际不符，数据格式也不正确，选择时间逻辑也不正确，请重新修改。', '2020-10-04 04:12:59', NULL, '2020-10-05 12:00:00', NULL, 6, 2, 0, '1.建立', 0),(4, '开发提醒模块页面优化', '80322020100410001', '优化开发页面的的模块设计，完成功能的要求。\n1.分页要求：要求每页显示合适的数据数，并进行合理的页面切换。\n2.内容和备注通过移动鼠标到达合适的单元格自动显示。\n3.时间显示更为美观。\n4.操作按钮布局更工整。', '2020-10-04 10:52:33', NULL, '2020-10-15 00:00:00', NULL, 0, 0, 0, '1.创建于2020年10月4日早上。', 0);
UNLOCK TABLES;
COMMIT;
