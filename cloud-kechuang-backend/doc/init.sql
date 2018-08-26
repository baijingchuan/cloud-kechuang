/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : training

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2017-10-17 15:13:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', 'JFinal Demo Title here', 'JFinal Demo Content here~~~~');
INSERT INTO `blog` VALUES ('2', 'test 1', 'test 1');
INSERT INTO `blog` VALUES ('3', 'test 2', 'test 2');
INSERT INTO `blog` VALUES ('4', 'test 3', 'test 3');

-- ----------------------------
-- Table structure for `gambit`
-- ----------------------------
DROP TABLE IF EXISTS `gambit`;
CREATE TABLE `gambit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `content` text COMMENT '内容',
  `img` varchar(100) DEFAULT NULL COMMENT '图片',
  `create_by` varchar(50) NOT NULL COMMENT '发布人',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='话题';

-- ----------------------------
-- Records of gambit
-- ----------------------------
INSERT INTO `gambit` VALUES ('1', 'test', null, 'yang', '1506954760974', null);

-- ----------------------------
-- Table structure for `group_announcement`
-- ----------------------------
DROP TABLE IF EXISTS `group_announcement`;
CREATE TABLE `group_announcement` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '公告内容',
  `read_count` int(11) DEFAULT '0' COMMENT '已读人数',
  `readers` varchar(200) DEFAULT NULL COMMENT '已读人id（id之间用“，”号隔开）',
  `create_by` varchar(50) DEFAULT NULL COMMENT '发布人',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='群组公告';

-- ----------------------------
-- Records of group_announcement
-- ----------------------------
INSERT INTO `group_announcement` VALUES ('1', 'test', 'test', '0', '', 'yang', '1506700725081', '1506700725081');
INSERT INTO `group_announcement` VALUES ('2', 'title', '123', '2', '4343432,132279201', 'yang', '1506701012371', '1506702414538');
INSERT INTO `group_announcement` VALUES ('3', '测试', '公告', '1', '132279201', 'yang', '1506737174832', '1506737289511');
INSERT INTO `group_announcement` VALUES ('5', 'test', 'test1', '0', null, 'yang', '1506871933794', '1506871964947');

-- ----------------------------
-- Table structure for `group_classification`
-- ----------------------------
DROP TABLE IF EXISTS `group_classification`;
CREATE TABLE `group_classification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `name` varchar(50) DEFAULT NULL COMMENT '群组分类名称',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='群分类';

-- ----------------------------
-- Records of group_classification
-- ----------------------------
INSERT INTO `group_classification` VALUES ('1', '娱乐', 'test');
INSERT INTO `group_classification` VALUES ('2', '餐饮', null);

-- ----------------------------
-- Table structure for `logging_event`
-- ----------------------------
DROP TABLE IF EXISTS `logging_event`;
CREATE TABLE `logging_event` (
  `timestmp` bigint(20) NOT NULL,
  `formatted_message` text NOT NULL,
  `logger_name` varchar(254) NOT NULL,
  `level_string` varchar(254) NOT NULL,
  `thread_name` varchar(254) DEFAULT NULL,
  `reference_flag` smallint(6) DEFAULT NULL,
  `arg0` varchar(254) DEFAULT NULL,
  `arg1` varchar(254) DEFAULT NULL,
  `arg2` varchar(254) DEFAULT NULL,
  `arg3` varchar(254) DEFAULT NULL,
  `caller_filename` varchar(254) NOT NULL,
  `caller_class` varchar(254) NOT NULL,
  `caller_method` varchar(254) NOT NULL,
  `caller_line` char(4) NOT NULL,
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logging_event
-- ----------------------------

-- ----------------------------
-- Table structure for `logging_event_exception`
-- ----------------------------
DROP TABLE IF EXISTS `logging_event_exception`;
CREATE TABLE `logging_event_exception` (
  `event_id` bigint(20) NOT NULL,
  `i` smallint(6) NOT NULL,
  `trace_line` text NOT NULL,
  PRIMARY KEY (`event_id`,`i`),
  CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logging_event_exception
-- ----------------------------

-- ----------------------------
-- Table structure for `logging_event_property`
-- ----------------------------
DROP TABLE IF EXISTS `logging_event_property`;
CREATE TABLE `logging_event_property` (
  `event_id` bigint(20) NOT NULL,
  `mapped_key` varchar(254) NOT NULL,
  `mapped_value` text,
  PRIMARY KEY (`event_id`,`mapped_key`),
  CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logging_event_property
-- ----------------------------

-- ----------------------------
-- Table structure for `member_device_number`
-- ----------------------------
DROP TABLE IF EXISTS `member_device_number`;
CREATE TABLE `member_device_number` (
  `member_id` int(11) NOT NULL COMMENT '用户ID',
  `device_number` varchar(100) DEFAULT NULL COMMENT '设备唯一标识',
  `device_type` tinyint(1) DEFAULT NULL COMMENT '设备类型(1:Android、2:ios)',
  `curr_ver_code` int(8) DEFAULT NULL COMMENT '用户使用的版本,',
  `cer_type` char(1) DEFAULT NULL COMMENT '1为个人;2为企业',
  `token` varchar(100) DEFAULT NULL COMMENT 'token令牌',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备信息';

-- ----------------------------
-- Records of member_device_number
-- ----------------------------

-- ----------------------------
-- Table structure for `message_record`
-- ----------------------------
DROP TABLE IF EXISTS `message_record`;
CREATE TABLE `message_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `msg_type` char(1) DEFAULT NULL COMMENT '消息类型 1文本（含表情）2图片3语音4文件',
  `msg_content` text COMMENT '消息内容',
  `msg_from` varchar(50) DEFAULT NULL COMMENT '发送人',
  `msg_to` varchar(50) DEFAULT NULL COMMENT '接收人',
  `group_id` int(11) NOT NULL COMMENT '群组id',
  `send_time` bigint(13) DEFAULT NULL COMMENT '发送时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息记录';

-- ----------------------------
-- Records of message_record
-- ----------------------------

-- ----------------------------
-- Table structure for `multimedia_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `multimedia_warehouse`;
CREATE TABLE `multimedia_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `media_name` varchar(100) DEFAULT NULL COMMENT '媒体名称',
  `media_describe` varchar(100) DEFAULT NULL COMMENT '媒体描述',
  `media_path` varchar(100) DEFAULT NULL COMMENT '媒体储存路径',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多媒体仓库(储存用户上传的视频文件)';

-- ----------------------------
-- Records of multimedia_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `my_friends`
-- ----------------------------
DROP TABLE IF EXISTS `my_friends`;
CREATE TABLE `my_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `host_id` int(11) NOT NULL COMMENT '发送者id',
  `friend_id` int(11) NOT NULL COMMENT '好友id',
  `friend_remark` varchar(50) DEFAULT NULL COMMENT '好友备注',
  `verification_information` varchar(50) DEFAULT NULL COMMENT '验证信息',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '好友状态 0：等待对方同意加为好友，1：已加为好友、2：对方拒绝加为好友、3：好友被删除',
  `friends_group_id` int(11) NOT NULL COMMENT '好友分组id（默认分组为“我的好友”）',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='我的好友';

-- ----------------------------
-- Records of my_friends
-- ----------------------------
INSERT INTO `my_friends` VALUES ('2', '10', '14', null, '我是yang', '1', '1', '1507978897982', '1507981229663');
INSERT INTO `my_friends` VALUES ('3', '14', '10', '888', null, '1', '1', '1507981229655', '1507984864740');

-- ----------------------------
-- Table structure for `my_friends_group`
-- ----------------------------
DROP TABLE IF EXISTS `my_friends_group`;
CREATE TABLE `my_friends_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(50) DEFAULT NULL COMMENT '好友分组名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '是否为系统分组0：否，1：是  （分组中只有“我的好友“是系统分组且不可删除）',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='我的好友分组';

-- ----------------------------
-- Records of my_friends_group
-- ----------------------------
INSERT INTO `my_friends_group` VALUES ('1', '0', '我的好友', '1', null);
INSERT INTO `my_friends_group` VALUES ('2', '0', '黑名单', '1', null);
INSERT INTO `my_friends_group` VALUES ('3', '11', '投资商', '0', null);

-- ----------------------------
-- Table structure for `red_envelope`
-- ----------------------------
DROP TABLE IF EXISTS `red_envelope`;
CREATE TABLE `red_envelope` (
  `red_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `publisher_member_id` int(11) NOT NULL COMMENT '发红包人id',
  `money` decimal(8,2) DEFAULT NULL COMMENT '总金额',
  `publish_time` bigint(13) DEFAULT NULL COMMENT '发红包时间',
  `total_count` char(4) DEFAULT NULL COMMENT '抢红包总个数',
  `grab_count` int(4) DEFAULT NULL COMMENT '抢红包人个数',
  `lat` float DEFAULT NULL COMMENT '纬度',
  `lng` float DEFAULT NULL COMMENT '经度',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `receivers` varchar(200) DEFAULT NULL COMMENT '抢红包人id（id之间用“，”号隔开）',
  `content` varchar(100) DEFAULT NULL COMMENT '红包内容',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `moneys` varchar(900) DEFAULT NULL COMMENT '每个红包的钱数',
  `overtime` char(1) DEFAULT '0' COMMENT '(0 -no ,1-yes)',
  `has_paied` char(1) DEFAULT NULL COMMENT '(0 -no ,1-yes)',
  PRIMARY KEY (`red_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发红包列表';

-- ----------------------------
-- Records of red_envelope
-- ----------------------------

-- ----------------------------
-- Table structure for `red_grab`
-- ----------------------------
DROP TABLE IF EXISTS `red_grab`;
CREATE TABLE `red_grab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `red_id` int(11) NOT NULL COMMENT '红包id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '抢到的钱数',
  `create_time` bigint(13) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抢红包列表';

-- ----------------------------
-- Records of red_grab
-- ----------------------------

-- ----------------------------
-- Table structure for `red_record`
-- ----------------------------
DROP TABLE IF EXISTS `red_record`;
CREATE TABLE `red_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `tel` varchar(15) DEFAULT NULL COMMENT '手机号',
  `total_before` decimal(8,2) DEFAULT NULL COMMENT '原有红包余额',
  `total_after` decimal(8,2) DEFAULT NULL COMMENT '现在红包余额',
  `money` decimal(8,2) DEFAULT NULL COMMENT '变化金额',
  `red_id` int(11) NOT NULL COMMENT '红包id',
  `demo` varchar(45) DEFAULT NULL COMMENT '红包去向',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包记录表';

-- ----------------------------
-- Records of red_record
-- ----------------------------

-- ----------------------------
-- Table structure for `transfer_accounts_record`
-- ----------------------------
DROP TABLE IF EXISTS `transfer_accounts_record`;
CREATE TABLE `transfer_accounts_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '转账金额',
  `payer` varchar(50) DEFAULT NULL COMMENT '付款人',
  `payee` varchar(50) DEFAULT NULL COMMENT '收款人',
  `status` char(2) DEFAULT NULL COMMENT '交易状态 0：处理中，1：已发送、2：失败、00：成功、01：失败、03：部分退款、04：全部退款、05：退款中、99：结果未知、5：系统退款中',
  `create_time` bigint(13) DEFAULT NULL COMMENT '转账时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转账记录';

-- ----------------------------
-- Records of transfer_accounts_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '账号',
  `user_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `sex` char(2) DEFAULT '0' COMMENT '性别 0未设置 1男2女',
  `user_icon` varchar(320) DEFAULT NULL COMMENT '头像',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `phone_active` char(2) DEFAULT '0' COMMENT '手机号激活（0代表未激活，1代表已激活）',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `email_active` char(2) DEFAULT '0' COMMENT '邮箱激活（0代表未激活，1代表已激活）',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `wait_pay_count` decimal(10,0) DEFAULT '0' COMMENT '待付款数',
  `wait_receive_count` int(11) DEFAULT '0' COMMENT '待收获数',
  `user_level` tinyint(3) DEFAULT '1' COMMENT '用户等级（1注册会员2铜牌会员3银牌会员4金牌会员5钻石会员）',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13000000004 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('88878888', '88', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589', '15833274292', '0', '6223274766@qq.com', '0', '8ddcff3a80f4189ca1c9d4d902c3c909', '0', '0', '1', '1506524609166', null);
INSERT INTO `t_user` VALUES ('13000000001', null, '0', null, '13732279201', '0', '1198566763@qq.com', '0', '35faed374f96b62608e3c706c298d2a1a11548be29b56c135f272e8b08333e49', '0', '0', '1', '1508215277181', null);
INSERT INTO `t_user` VALUES ('13000000002', null, '0', null, '13732279201', '0', '1198566763@qq.com', '0', '35faed374f96b62608e3c706c298d2a1a11548be29b56c135f272e8b08333e49', '0', '0', '1', '1508215646603', null);
INSERT INTO `t_user` VALUES ('13000000003', null, '0', null, '13732279201', '0', '1198566763@qq.com', '0', '35faed374f96b62608e3c706c298d2a1a11548be29b56c135f272e8b08333e49', '0', '0', '1', '1508222049545', null);

-- ----------------------------
-- Table structure for `user_group`
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `group_name` varchar(50) DEFAULT NULL COMMENT '群组名称',
  `group_describe` varchar(100) DEFAULT NULL COMMENT '群组描述',
  `group_icon` varchar(100) DEFAULT NULL COMMENT '群组图片',
  `address` varchar(100) DEFAULT NULL COMMENT '所在地',
  `group_classification_id` int(11) NOT NULL COMMENT '群分类id',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改日期',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='群组信息表';

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES ('2', 'test', 'test', null, 'test', '1', '1507379610435', '1507389601818', null);
INSERT INTO `user_group` VALUES ('4', '商业合作', 'test', null, 'test', '2', '1507387815496', null, null);

-- ----------------------------
-- Table structure for `yx_adarea`
-- ----------------------------
DROP TABLE IF EXISTS `yx_adarea`;
CREATE TABLE `yx_adarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `title` varchar(50) DEFAULT NULL COMMENT '广告名称,',
  `flag` char(1) DEFAULT NULL COMMENT '广告类型1左侧广告2信息广告,',
  `link` varchar(150) DEFAULT NULL COMMENT '链接地址,',
  `start_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '开始时间,',
  `end_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '结束时间,',
  `img` varchar(150) DEFAULT NULL COMMENT '图片地址,',
  `agent_id` int(11) NOT NULL COMMENT '代理商id,',
  `ad_level` char(1) DEFAULT NULL COMMENT '广告级别1省级2市级,',
  `province_id` int(11) DEFAULT NULL COMMENT '省份id',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  `pass` char(1) DEFAULT NULL COMMENT '1通过审核0未通过,',
  `del` char(1) DEFAULT NULL COMMENT '1审核删除0正常,',
  `content` text COMMENT '广告内容,',
  `sm_logo` varchar(150) DEFAULT NULL COMMENT '缩略图,',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP广告位';

-- ----------------------------
-- Records of yx_adarea
-- ----------------------------

-- ----------------------------
-- Table structure for `yx_city`
-- ----------------------------
DROP TABLE IF EXISTS `yx_city`;
CREATE TABLE `yx_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `name` varchar(50) DEFAULT NULL COMMENT '城市名称,',
  `post_code` varchar(15) DEFAULT NULL COMMENT '城市编码,',
  `province_id` int(11) DEFAULT NULL COMMENT '省份id',
  `has_sign` char(1) NOT NULL DEFAULT '0' COMMENT '是否已签约(0:否、1:是),',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市列表';

-- ----------------------------
-- Records of yx_city
-- ----------------------------

-- ----------------------------
-- Table structure for `yx_district`
-- ----------------------------
DROP TABLE IF EXISTS `yx_district`;
CREATE TABLE `yx_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `district_name` varchar(50) DEFAULT NULL COMMENT '区县名称',
  `province_id` int(11) NOT NULL COMMENT '省份id',
  `city_id` int(11) NOT NULL COMMENT '城市id',
  `has_sign` char(1) DEFAULT NULL COMMENT '是否已签约(0:否、1:是),',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区列表';

-- ----------------------------
-- Records of yx_district
-- ----------------------------

-- ----------------------------
-- Table structure for `yx_my_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `yx_my_favorite`;
CREATE TABLE `yx_my_favorite` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `title` varchar(50) DEFAULT NULL COMMENT '广告名称,',
  `avorite_describe` varchar(100) DEFAULT NULL COMMENT '描述',
  `avorite_label` varchar(50) DEFAULT NULL COMMENT '标签',
  `link` varchar(150) DEFAULT NULL COMMENT '链接地址,',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的收藏';

-- ----------------------------
-- Records of yx_my_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for `yx_order_comment`
-- ----------------------------
DROP TABLE IF EXISTS `yx_order_comment`;
CREATE TABLE `yx_order_comment` (
  `gambit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID,',
  `comment_member_id` int(11) NOT NULL COMMENT '评论者id,',
  `commented_member_id` int(11) DEFAULT NULL COMMENT '被评论者id,',
  `time` bigint(13) DEFAULT NULL COMMENT '评论时间,',
  `content` varchar(300) DEFAULT NULL COMMENT '评价内容,',
  `type` char(1) DEFAULT NULL COMMENT '1：发布人对评论人的回复；2评论人对发布人,',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`gambit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单评论表';

-- ----------------------------
-- Records of yx_order_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `yx_province`
-- ----------------------------
DROP TABLE IF EXISTS `yx_province`;
CREATE TABLE `yx_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `name` varchar(50) DEFAULT NULL COMMENT '省名称,',
  `has_sign` char(1) DEFAULT NULL COMMENT '是否已签约(0:否、1:是),',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份列表';

-- ----------------------------
-- Records of yx_province
-- ----------------------------
