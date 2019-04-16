/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : antfinancial

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 16/04/2019 23:28:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userId` int(11) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `state` int(2) NOT NULL DEFAULT 0 COMMENT '状态 0:未激活，1:激活，2:冻结',
  `packageJ` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT 'J钱包(静态钱包',
  `packageD` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT 'D钱包(动态钱包',
  `packageZ` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT 'Z钱包(注册用户',
  `alipayNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '支付宝账号',
  `webcatNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微信号',
  `bankName` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '银行',
  `bankNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `packageNum` int(4) NOT NULL COMMENT '增值包数量',
  `profit` double(11, 2) NOT NULL DEFAULT 0.00 COMMENT '收益',
  `createTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '修改时间',
  `activeTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '激活时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES (1, 1, 1, 5000.00, 5001.00, 5000.00, 'zifubao.com', 'webcat', '中国银行', '6222021602015544788', 1, 0.00, '2017-06-29 11:18:07', '2017-06-29 11:18:07', '2017-06-29 11:18:07');
INSERT INTO `t_account` VALUES (2, 2, 1, 5000.00, 5000.00, 5000.00, 'zifubao.com', 'webcat', '中国银行', '6222021602015544788', 1, 0.00, '2017-06-29 11:18:07', '2017-06-29 11:18:07', '2017-06-29 11:18:07');
INSERT INTO `t_account` VALUES (3, 3, 1, 5000.00, 5000.00, 5000.00, 'zifubao.com', 'webcat', '中国银行', '6222021602015544788', 1, 0.00, '2017-06-29 11:18:07', '2017-06-29 11:18:07', '2017-06-29 11:18:07');
INSERT INTO `t_account` VALUES (4, 4, 1, 5000.00, 5000.00, 5000.00, 'zifubao.com', 'webcat', '中国银行', '6222021602015544788', 1, 0.00, '2017-06-29 11:18:07', '2017-06-29 11:18:07', '2017-06-29 11:18:07');

-- ----------------------------
-- Table structure for t_capital_flow
-- ----------------------------
DROP TABLE IF EXISTS `t_capital_flow`;
CREATE TABLE `t_capital_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activeUserId` int(11) DEFAULT NULL,
  `amount` double NOT NULL,
  `buyUserId` int(11) DEFAULT NULL,
  `createDateTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `fee` double NOT NULL,
  `packageType` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_capital_flow
-- ----------------------------
INSERT INTO `t_capital_flow` VALUES (1, NULL, 1, NULL, '2019-04-16 20:51:09', 0, 1, 11, 1);

-- ----------------------------
-- Table structure for t_gold_award
-- ----------------------------
DROP TABLE IF EXISTS `t_gold_award`;
CREATE TABLE `t_gold_award`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `amountType` int(11) DEFAULT NULL,
  `childUserId` int(11) DEFAULT NULL,
  `createDateTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `profitType` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_login_history
-- ----------------------------
DROP TABLE IF EXISTS `t_login_history`;
CREATE TABLE `t_login_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `loginIp` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userRole` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_login_history
-- ----------------------------
INSERT INTO `t_login_history` VALUES (1, '2019-04-11 21:12:43', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (2, '2019-04-15 21:50:47', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (3, '2019-04-16 20:50:48', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (4, '2019-04-16 21:13:10', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (5, '2019-04-16 21:32:22', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (6, '2019-04-16 21:34:52', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (7, '2019-04-16 21:36:15', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (8, '2019-04-16 21:38:44', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (9, '2019-04-16 21:44:47', '192.168.2.217', 1, 1);
INSERT INTO `t_login_history` VALUES (10, '2019-04-16 21:53:31', '192.168.2.217', 1, 1);

-- ----------------------------
-- Table structure for t_manage_profit_history
-- ----------------------------
DROP TABLE IF EXISTS `t_manage_profit_history`;
CREATE TABLE `t_manage_profit_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `createId` int(11) DEFAULT NULL,
  `sendRole` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `orderCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `packageType` int(11) DEFAULT NULL,
  `remain` double NOT NULL,
  `state` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_transaction
-- ----------------------------
DROP TABLE IF EXISTS `t_order_transaction`;
CREATE TABLE `t_order_transaction`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `buyId` int(11) DEFAULT NULL,
  `buyUserId` int(11) DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `imgSrc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `saleId` int(11) DEFAULT NULL,
  `saleUserId` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `updateTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_profit
-- ----------------------------
DROP TABLE IF EXISTS `t_profit`;
CREATE TABLE `t_profit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_promotion_award
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion_award`;
CREATE TABLE `t_promotion_award`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `teamLevel` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sign`;
CREATE TABLE `t_sign`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_code`;
CREATE TABLE `t_sms_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_static_profit
-- ----------------------------
DROP TABLE IF EXISTS `t_static_profit`;
CREATE TABLE `t_static_profit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `createDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `packageNum` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sysparam
-- ----------------------------
DROP TABLE IF EXISTS `t_sysparam`;
CREATE TABLE `t_sysparam`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `keyName` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量名',
  `val` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量值',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1007 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sysparam
-- ----------------------------
INSERT INTO `t_sysparam` VALUES (1, 'PUBLIC_PASSWORD', '862a521f876e399e7250d239f30c0483', '公共密码');
INSERT INTO `t_sysparam` VALUES (2, 'ATTENUATION', '1', '衰减值(天)');
INSERT INTO `t_sysparam` VALUES (3, 'DEADINE', '24', '交易过期时长(小时)');
INSERT INTO `t_sysparam` VALUES (4, 'USERUPDATE', '0', '用户信息修改开关 0:开 1:关');
INSERT INTO `t_sysparam` VALUES (5, 'ACTIVE_AMOUNT', '500', '激活用户费用');
INSERT INTO `t_sysparam` VALUES (6, 'ACTIVE_PERCENTAGE', '0', '激活用户平台提成(百分比)');
INSERT INTO `t_sysparam` VALUES (7, 'ACTIVE_AWARD', '5', '激活用户奖励');
INSERT INTO `t_sysparam` VALUES (8, 'ACTIVE_AWARD_TOP', '50', '激活用户最高奖励');
INSERT INTO `t_sysparam` VALUES (9, 'SIGN_AWARD', '5', '签到奖励');
INSERT INTO `t_sysparam` VALUES (10, 'PACKAGE_TOP_NUM', '20', '增值包最大数');
INSERT INTO `t_sysparam` VALUES (11, 'PACKAGE_AMOUNT', '500', '开启增值包费用');
INSERT INTO `t_sysparam` VALUES (12, 'APP_KEY', 'LTAIwwPb2BAVB2wu', '短信平台APPKEY');
INSERT INTO `t_sysparam` VALUES (13, 'APP_SECRET', 'SGJYBHkH8MP4ocBcMehUCLjW1O9ivS', '短信平台APPSECRET');
INSERT INTO `t_sysparam` VALUES (14, 'NONCE', '0123456789', '短信平台随机数');
INSERT INTO `t_sysparam` VALUES (15, 'TEMPLATEID_VALIDATE', 'SMS_140665227', '短信平台验证码模板ID');
INSERT INTO `t_sysparam` VALUES (16, 'CODELEN', '6', '短信平台短信长度');
INSERT INTO `t_sysparam` VALUES (17, 'TEMPLATEID_NOTICE', '3063650', '短信平台通知模板ID');
INSERT INTO `t_sysparam` VALUES (18, 'POUNDAGE_LEVEL_1', '0', '卖出时收取费用(增值包开启20个)');
INSERT INTO `t_sysparam` VALUES (19, 'POUNDAGE_LEVEL_2', '0.05', '卖出时收取费用(增值包开启16-19个)');
INSERT INTO `t_sysparam` VALUES (20, 'POUNDAGE_LEVEL_3', '0.1', '卖出时收取费用(增值包开启11-15个)');
INSERT INTO `t_sysparam` VALUES (21, 'POUNDAGE_LEVEL_4', '0.15', '卖出时收取费用(增值包开启6-10个)');
INSERT INTO `t_sysparam` VALUES (22, 'POUNDAGE_LEVEL_5', '0.2', '卖出时收取费用(增值包开启0-5个)');
INSERT INTO `t_sysparam` VALUES (23, 'PACKAGE_SELFE_PROFIT_TO_Z', '0.2', '个人增值包收益存入Z钱包的百分比');
INSERT INTO `t_sysparam` VALUES (24, 'PACKAGE_SELFE_PROFIT_TO_J', '0.8', '个人增值包收益存入J钱包的百分比');
INSERT INTO `t_sysparam` VALUES (25, 'PACKAGE_PROFIT_LEVEL_1', '0.1', '一级会员收益百分比');
INSERT INTO `t_sysparam` VALUES (26, 'PACKAGE_PROFIT_LEVEL_2', '0.05', '二级会员收益百分比');
INSERT INTO `t_sysparam` VALUES (27, 'PACKAGE_PROFIT_LEVEL_3', '0.03', '三级会员收益百分比');
INSERT INTO `t_sysparam` VALUES (28, 'PACKAGE_CHILD_PROFIT_TO_D', '0.7', '动态收益存入D钱包的百分比');
INSERT INTO `t_sysparam` VALUES (29, 'PACKAGE_CHILD_PROFIT_TO_Z', '0.3', '动态收益存入Z钱包的百分比');
INSERT INTO `t_sysparam` VALUES (30, 'MAX_TRANSATION_AMOUNT_SALE', '50000', '每月最高卖出金额');
INSERT INTO `t_sysparam` VALUES (31, 'MAX_TRANSATION_AMOUNT_BUY', '50000', '每月最高买入金额');
INSERT INTO `t_sysparam` VALUES (32, 'MANAGER_PROFIT_PERCENTAGE', '0.1', '特殊账号收取的收益百分比');
INSERT INTO `t_sysparam` VALUES (33, 'FIRST_LEVEL_COUNT', '3', '一级用户收益收取人数');
INSERT INTO `t_sysparam` VALUES (34, 'SECOND_LEVEL_COUNT', '5', '二级用户收益收取人数');
INSERT INTO `t_sysparam` VALUES (35, 'THIRD_LEVEL_COUNT', '10', '三级用户收益收取人数');
INSERT INTO `t_sysparam` VALUES (36, 'RECHANGE_ACCOUNT', '15000000000', 'EOS充值账户');
INSERT INTO `t_sysparam` VALUES (37, 'RECHANGE_IMG_URL', 'http://localhost:8080/antfinancial_Web_exploded/upload/imgs/20190416/1555422293202_638.jpg', 'EOS充值二维码地址');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userAccount` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `userPassword` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户姓名',
  `referenceAccount` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '推荐人手机号码',
  `referenceId` int(11) NOT NULL DEFAULT 0 COMMENT '推荐人ID，0：系统初始用户',
  `createTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '注册时间',
  `isDel` int(2) NOT NULL DEFAULT 0 COMMENT '是否已删除 0:未删除 1:已删除',
  `userRole` int(2) NOT NULL DEFAULT 0 COMMENT '用户角色 0:会员用户 1:管理员 2:卖出手续费账号 3:收益账号',
  `loginState` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '登陆时间戳',
  `userCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '用户唯一编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '18000000000', '21232f297a57a5a743894a0e4a801fc3', '管理员', '0', 0, '2017-06-29 11:18:07', 0, 1, '0', '00000000');
INSERT INTO `t_user` VALUES (2, '18100000000', '21232f297a57a5a743894a0e4a801fc3', '系统会员A', '0', 0, '2017-06-29 11:18:07', 0, 0, '0', '00000001');
INSERT INTO `t_user` VALUES (3, '18200000000', '21232f297a57a5a743894a0e4a801fc3', '卖出手续费账号', '0', 0, '2017-06-29 11:18:07', 0, 2, '0', '00000002');
INSERT INTO `t_user` VALUES (4, '18300000000', '21232f297a57a5a743894a0e4a801fc3', '收益账号', '0', 0, '2017-06-29 11:18:07', 0, 3, '0', '00000003');

-- ----------------------------
-- Table structure for t_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `t_withdraw`;
CREATE TABLE `t_withdraw`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `account_type` int(11) NOT NULL DEFAULT 0 COMMENT '账户类型 0J 1D 2Z',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '收款地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注信息',
  `amount` decimal(8, 0) NOT NULL COMMENT '金额',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态 0未确认 1已确认 2已取消',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
