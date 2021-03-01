/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50650
Source Host           : localhost:3306
Source Database       : scheduletask

Target Server Type    : MYSQL
Target Server Version : 50650
File Encoding         : 65001

Date: 2021-03-01 16:23:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task_cron
-- ----------------------------
DROP TABLE IF EXISTS `task_cron`;
CREATE TABLE `task_cron` (
  `cron_id` varchar(30) NOT NULL,
  `cron` varchar(30) NOT NULL COMMENT '定时任务时间配置',
  PRIMARY KEY (`cron_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of task_cron
-- ----------------------------
INSERT INTO `task_cron` VALUES ('1', '0/5 * * * * ?');
