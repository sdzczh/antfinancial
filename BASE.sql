/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.5.56 : Database - antfinancial
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`antfinancial` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `antfinancial`;

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `state` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:未激活，1:激活，2:冻结',
  `packageJ` double(11,2) NOT NULL DEFAULT '0.00' COMMENT 'J钱包(静态钱包',
  `packageD` double(11,2) NOT NULL DEFAULT '0.00' COMMENT 'D钱包(动态钱包',
  `packageZ` double(11,2) NOT NULL DEFAULT '0.00' COMMENT 'Z钱包(注册用户',
  `alipayNumber` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付宝账号',
  `webcatNumber` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '微信号',
  `bankName` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '银行',
  `bankNumber` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `packageNum` int(4) NOT NULL COMMENT '增值包数量',
  `profit` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '收益',
  `createTime` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateTime` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '修改时间',
  `activeTime` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '激活时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_account` */

insert  into `t_account`(`id`,`userId`,`state`,`packageJ`,`packageD`,`packageZ`,`alipayNumber`,`webcatNumber`,`bankName`,`bankNumber`,`packageNum`,`profit`,`createTime`,`updateTime`,`activeTime`) values (1,1,1,5000.00,5000.00,5000.00,'zifubao.com','webcat','中国银行','6222021602015544788',1,0.00,'2017-06-29 11:18:07','2017-06-29 11:18:07','2017-06-29 11:18:07'),(2,2,1,5000.00,5000.00,5000.00,'zifubao.com','webcat','中国银行','6222021602015544788',1,0.00,'2017-06-29 11:18:07','2017-06-29 11:18:07','2017-06-29 11:18:07'),(3,3,1,5000.00,5000.00,5000.00,'zifubao.com','webcat','中国银行','6222021602015544788',1,0.00,'2017-06-29 11:18:07','2017-06-29 11:18:07','2017-06-29 11:18:07'),(4,4,1,5000.00,5000.00,5000.00,'zifubao.com','webcat','中国银行','6222021602015544788',1,0.00,'2017-06-29 11:18:07','2017-06-29 11:18:07','2017-06-29 11:18:07');

/*Table structure for table `t_sysparam` */

DROP TABLE IF EXISTS `t_sysparam`;

CREATE TABLE `t_sysparam` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `keyName` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '变量名',
  `val` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '变量值',
  `remark` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_sysparam` */

insert  into `t_sysparam`(`id`,`keyName`,`val`,`remark`) values (1,'PUBLIC_PASSWORD','862a521f876e399e7250d239f30c0483','公共密码'),(2,'ATTENUATION','0','衰减值(天)'),(3,'DEADINE','24','交易过期时长(小时)'),(4,'USERUPDATE','1','用户信息修改开关 0:开 1:关'),(5,'ACTIVE_AMOUNT','500','激活用户费用'),(6,'ACTIVE_PERCENTAGE','0','激活用户平台提成(百分比)'),(7,'ACTIVE_AWARD','5','激活用户奖励'),(8,'ACTIVE_AWARD_TOP','50','激活用户最高奖励'),(9,'SIGN_AWARD','5','签到奖励'),(10,'PACKAGE_TOP_NUM','20','增值包最大数'),(11,'PACKAGE_AMOUNT','500','开启增值包费用'),(12,'APP_KEY','c34e812c7986bb23eb9a186e9467116a','短信平台APPKEY'),(13,'APP_SECRET','39e057e527c9','短信平台APPSECRET'),(14,'NONCE','0123456789','短信平台随机数'),(15,'TEMPLATEID_VALIDATE','3063575','短信平台验证码模板ID'),(16,'CODELEN','6','短信平台短信长度'),(17,'TEMPLATEID_NOTICE','3063650','短信平台通知模板ID'),(18,'POUNDAGE_LEVEL_1','0','卖出时收取费用(增值包开启20个)'),(19,'POUNDAGE_LEVEL_2','0.05','卖出时收取费用(增值包开启16-19个)'),(20,'POUNDAGE_LEVEL_3','0.1','卖出时收取费用(增值包开启11-15个)'),(21,'POUNDAGE_LEVEL_4','0.15','卖出时收取费用(增值包开启6-10个)'),(22,'POUNDAGE_LEVEL_5','0.2','卖出时收取费用(增值包开启0-5个)'),(23,'PACKAGE_SELFE_PROFIT_TO_Z','0.2','个人增值包收益存入Z钱包的百分比'),(24,'PACKAGE_SELFE_PROFIT_TO_J','0.8','个人增值包收益存入J钱包的百分比'),(25,'PACKAGE_PROFIT_LEVEL_1','0.1','一级会员收益百分比'),(26,'PACKAGE_PROFIT_LEVEL_2','0.05','二级会员收益百分比'),(27,'PACKAGE_PROFIT_LEVEL_3','0.03','三级会员收益百分比'),(28,'PACKAGE_CHILD_PROFIT_TO_D','0.7','动态收益存入D钱包的百分比'),(29,'PACKAGE_CHILD_PROFIT_TO_Z','0.3','动态收益存入Z钱包的百分比'),(30,'MAX_TRANSATION_AMOUNT_SALE','50000','每月最高卖出金额'),(31,'MAX_TRANSATION_AMOUNT_BUY','50000','每月最高买入金额'),(32,'MANAGER_PROFIT_PERCENTAGE','0.1','特殊账号收取的收益百分比'),(33,'FIRST_LEVEL_COUNT','3','一级用户收益收取人数'),(34,'SECOND_LEVEL_COUNT','5','二级用户收益收取人数'),(35,'THIRD_LEVEL_COUNT','10','三级用户收益收取人数');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userAccount` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `userPassword` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `userName` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户姓名',
  `referenceAccount` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '推荐人手机号码',
  `referenceId` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人ID，0：系统初始用户',
  `createTime` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '注册时间',
  `isDel` int(2) NOT NULL DEFAULT '0' COMMENT '是否已删除 0:未删除 1:已删除',
  `userRole` int(2) NOT NULL DEFAULT '0' COMMENT '用户角色 0:会员用户 1:管理员 2:卖出手续费账号 3:收益账号',
  `loginState` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '登陆时间戳',
  `userCode` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '用户唯一编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userAccount`,`userPassword`,`userName`,`referenceAccount`,`referenceId`,`createTime`,`isDel`,`userRole`,`loginState`,`userCode`) values (1,'18000000000','21232f297a57a5a743894a0e4a801fc3','管理员','0',0,'2017-06-29 11:18:07',0,1,'0','00000000'),(2,'18100000000','21232f297a57a5a743894a0e4a801fc3','系统会员A','0',0,'2017-06-29 11:18:07',0,0,'0','00000001'),(3,'18200000000','21232f297a57a5a743894a0e4a801fc3','卖出手续费账号','0',0,'2017-06-29 11:18:07',0,2,'0','00000002'),(4,'18300000000','21232f297a57a5a743894a0e4a801fc3','收益账号','0',0,'2017-06-29 11:18:07',0,3,'0','00000003');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
