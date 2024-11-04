/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : financial

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001


*/

CREATE DATABASE IF NOT EXISTS financial default charset utf8 COLLATE utf8_general_ci;

USE financial;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255)  NULL DEFAULT NULL,
  `userid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `money` float(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `typeid` int(0) NOT NULL COMMENT '类型 1 收入 2 支出',
  `remark` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `paywayid` int(0) NULL DEFAULT NULL COMMENT '支付方式',
  `time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  INDEX `type`(`typeid`) USING BTREE,
  INDEX `payway`(`paywayid`) USING BTREE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`typeid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bill_ibfk_3` FOREIGN KEY (`paywayid`) REFERENCES `payway` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 199 ;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES (195, '嘻嘻嘻', 1, 1000.00, 1, '嘻嘻嘻嘻嘻嘻', 1, '2021-04-13 21:03:40');
INSERT INTO `bill` VALUES (196, '哈哈', 1, 100.00, 2, '嘻嘻嘻', 1, '2021-04-14 08:09:21');
INSERT INTO `bill` VALUES (197, '哈哈', 1, 100.00, 2, '嘻嘻嘻', 1, '2021-04-14 08:12:15');
INSERT INTO `bill` VALUES (198, '哈哈', 1, 100.00, 1, '嘻嘻嘻', 1, '2021-04-14 08:12:30');

-- ----------------------------
-- Table structure for budget
-- ----------------------------
DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255)  NULL DEFAULT NULL,
  `content` varchar(500)  NULL DEFAULT NULL,
  `money` decimal(10, 2) NULL DEFAULT NULL,
  `cost` decimal(10, 2) NULL DEFAULT NULL,
  `dateTime` varchar(50)  NULL DEFAULT NULL,
  `remark` varchar(255)  NULL DEFAULT NULL,
  `completeTime` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8  ;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (1, '333', '333', 3.00, 33.00, '3', '3', '3');

-- ----------------------------
-- Table structure for callpay
-- ----------------------------
DROP TABLE IF EXISTS `callpay`;
CREATE TABLE `callpay`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `customerId` int(0) NULL DEFAULT NULL COMMENT '客户id',
  `callNo` varchar(255)  NULL DEFAULT NULL COMMENT '账单号',
  `cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态',
  `payTime` varchar(255)  NULL DEFAULT NULL COMMENT '支付时间',
  `expireTime` varchar(255)  NULL DEFAULT NULL COMMENT '到期时间',
  `createTime` varchar(255)  NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4  ;

-- ----------------------------
-- Records of callpay
-- ----------------------------
INSERT INTO `callpay` VALUES (3, 1, '10001', 1000.00, 1, '2021-05-02 08:43:38', '2021-05-18 00:04:04', '2021-05-02 08:39:29');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cId` int(0) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(255)  NULL DEFAULT NULL,
  `cName` varchar(255)  NULL DEFAULT NULL,
  `password` varchar(255)  NULL DEFAULT NULL,
  `phone` varchar(255)  NULL DEFAULT NULL,
  `company` varchar(255)  NULL DEFAULT NULL,
  `address` varchar(255)  NULL DEFAULT NULL,
  `email` varchar(255)  NULL DEFAULT NULL,
  `createTime` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`cId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5  ;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (2, '1', '李四', '1', '15797370255', '哈哈哈公司2', '上海市徐汇区2', '2674410423@qq.com', '2021-05-01 05:47:27');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `empNo` varchar(255)  NULL DEFAULT NULL,
  `username` varchar(255)  NULL DEFAULT NULL,
  `password` varchar(255)  NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `sex` varchar(255)  NULL DEFAULT NULL,
  `salary` decimal(10, 2) NULL DEFAULT NULL,
  `subsidy` decimal(10, 2) NULL DEFAULT NULL,
  `socialSecurity` decimal(10, 2) NULL DEFAULT NULL,
  `dateTime` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4  ;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (1, '101', '张三', '101', 35, '男', 10000.00, 1000.00, 100.00, '2020-02-02');
INSERT INTO `emp` VALUES (3, '4444', 'yy', '122344', 33, '男', 10000.00, 199.00, 1000.00, '2021-05-05 03:58:22');

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ownerid` int(0) NOT NULL COMMENT '户主编号',
  `address` varchar(255)  NULL DEFAULT NULL COMMENT '家庭住址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `holderid`(`ownerid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 ;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (1, 3, '红谷滩新区');
INSERT INTO `house` VALUES (3, 20, NULL);
INSERT INTO `house` VALUES (4, 21, NULL);

-- ----------------------------
-- Table structure for payway
-- ----------------------------
DROP TABLE IF EXISTS `payway`;
CREATE TABLE `payway`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `payway` varchar(255)  NULL DEFAULT NULL,
  `extra` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 ;

-- ----------------------------
-- Records of payway
-- ----------------------------
INSERT INTO `payway` VALUES (1, '支付宝', NULL);
INSERT INTO `payway` VALUES (2, '微信', NULL);
INSERT INTO `payway` VALUES (3, '银联', NULL);
INSERT INTO `payway` VALUES (4, '现金', NULL);
INSERT INTO `payway` VALUES (5, '其他', NULL);

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `privilegeNumber` varchar(80)  NULL DEFAULT NULL COMMENT '权限编号',
  `privilegeName` varchar(80)  NULL DEFAULT NULL COMMENT '权限名称',
  `privilegeTipflag` char(4)  NULL DEFAULT NULL COMMENT '菜单级别',
  `privilegeTypeflag` char(4)  NULL DEFAULT NULL COMMENT '1启用 0禁用',
  `privilegeUrl` varchar(255)  NULL DEFAULT NULL COMMENT '权限URL',
  `icon` varchar(20)  NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 ;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES (62, '001', '支出管理', '0', '1', '', '&#xe698;');
INSERT INTO `privilege` VALUES (63, '001001', '支出详情', '1', '1', 'pay_details', '&#xe698;');
INSERT INTO `privilege` VALUES (64, '002', '收入管理', '0', '1', NULL, '&#xe702;');
INSERT INTO `privilege` VALUES (65, '002001', '收入详情', '1', '1', 'income_details', '&#xe702;');
INSERT INTO `privilege` VALUES (66, '003', '统计报表', '0', '1', NULL, '&#xe757;');
INSERT INTO `privilege` VALUES (67, '003001', '统计报表', '1', '1', 'chart_line', '&#xe757;');
INSERT INTO `privilege` VALUES (68, '004', '家庭成员管理', '0', '1', NULL, '&#xe726;');
INSERT INTO `privilege` VALUES (69, '005', '系统管理', '0', '1', '', '&#xe696;');
INSERT INTO `privilege` VALUES (70, '005001', '用户管理', '1', '1', 'sys_users', '&#xe6b8;');
INSERT INTO `privilege` VALUES (71, '005002', '角色管理', '1', '1', 'sys_roles', '&#xe70b;');
INSERT INTO `privilege` VALUES (74, '004001', '家庭成员信息', '1', '1', 'sys_users', '&#xe726;');
INSERT INTO `privilege` VALUES (75, '006', '预算管理', '0', '1', '', '&#xe696;');
INSERT INTO `privilege` VALUES (76, '006001', '预算列表', '1', '1', 'budget_budget-list', '&#xe696;');
INSERT INTO `privilege` VALUES (77, '007', '员工管理', '0', '1', '', '&#xe696;');
INSERT INTO `privilege` VALUES (78, '007001', '员工列表', '1', '1', 'emp_emp-list', '&#xe696;');
INSERT INTO `privilege` VALUES (79, '008', '客户管理', '0', '1', NULL, '&#xe696;');
INSERT INTO `privilege` VALUES (80, '008001', '客户列表', '1', '1', 'customer_customer-list', '&#xe696;');
INSERT INTO `privilege` VALUES (81, '009', '催缴管理', '0', '1', NULL, '&#xe696;');
INSERT INTO `privilege` VALUES (82, '009001', '催缴列表', '1', '1', 'callpay_callpay-list', '&#xe696;');
INSERT INTO `privilege` VALUES (83, '010', '报表管理', '0', '1', NULL, '&#xe696;');
INSERT INTO `privilege` VALUES (84, '010001', '报表列表', '1', '1', 'report_report-list', '&#xe696;');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `reportName` varchar(255)  NULL DEFAULT NULL,
  `reportPurpose` varchar(255)  NULL DEFAULT NULL,
  `reportPath` varchar(255)  NULL DEFAULT NULL,
  `dateTime` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2  ;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleid` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rolename` varchar(255)  NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 ;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员');
INSERT INTO `role` VALUES (3, '用户');

-- ----------------------------
-- Table structure for roleprivilieges
-- ----------------------------
DROP TABLE IF EXISTS `roleprivilieges`;
CREATE TABLE `roleprivilieges`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleID` int(0) NULL DEFAULT NULL COMMENT '角色维护表主键',
  `privilegeID` int(0) NULL DEFAULT NULL COMMENT '权限维护表主键',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `roleID`(`roleID`) USING BTREE,
  INDEX `privilegeID`(`privilegeID`) USING BTREE,
  CONSTRAINT `roleprivilieges_ibfk_1` FOREIGN KEY (`roleID`) REFERENCES `role` (`roleid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `roleprivilieges_ibfk_2` FOREIGN KEY (`privilegeID`) REFERENCES `privilege` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 877 ;

-- ----------------------------
-- Records of roleprivilieges
-- ----------------------------
INSERT INTO `roleprivilieges` VALUES (829, 1, 62);
INSERT INTO `roleprivilieges` VALUES (830, 1, 63);
INSERT INTO `roleprivilieges` VALUES (831, 1, 64);
INSERT INTO `roleprivilieges` VALUES (832, 1, 65);
INSERT INTO `roleprivilieges` VALUES (833, 1, 66);
INSERT INTO `roleprivilieges` VALUES (834, 1, 67);
INSERT INTO `roleprivilieges` VALUES (836, 1, 69);
INSERT INTO `roleprivilieges` VALUES (837, 1, 70);
INSERT INTO `roleprivilieges` VALUES (838, 1, 71);
INSERT INTO `roleprivilieges` VALUES (847, 3, 62);
INSERT INTO `roleprivilieges` VALUES (848, 3, 63);
INSERT INTO `roleprivilieges` VALUES (849, 3, 64);
INSERT INTO `roleprivilieges` VALUES (850, 3, 65);
INSERT INTO `roleprivilieges` VALUES (853, 3, 66);
INSERT INTO `roleprivilieges` VALUES (854, 3, 67);
INSERT INTO `roleprivilieges` VALUES (867, 1, 75);
INSERT INTO `roleprivilieges` VALUES (868, 1, 76);
INSERT INTO `roleprivilieges` VALUES (869, 1, 77);
INSERT INTO `roleprivilieges` VALUES (870, 1, 78);
INSERT INTO `roleprivilieges` VALUES (871, 1, 79);
INSERT INTO `roleprivilieges` VALUES (872, 1, 80);
INSERT INTO `roleprivilieges` VALUES (873, 1, 81);
INSERT INTO `roleprivilieges` VALUES (874, 1, 82);
INSERT INTO `roleprivilieges` VALUES (875, 1, 83);
INSERT INTO `roleprivilieges` VALUES (876, 1, 84);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(0) NOT NULL,
  `name` varchar(255)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '支出');
INSERT INTO `type` VALUES (2, '收入');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255)  NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255)  NULL DEFAULT NULL COMMENT '密码',
  `realname` varchar(255)  NULL DEFAULT NULL COMMENT '真实姓名',
  `roleid` int(0) NOT NULL DEFAULT 3 COMMENT '角色编号',
  `photo` varchar(255)  NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleid`(`roleid`) USING BTREE,
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 ;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '系统管理员', 1, NULL);
INSERT INTO `user` VALUES (25, 'aa', '123456', 'aaaaa', 3, NULL);

SET FOREIGN_KEY_CHECKS = 1;
