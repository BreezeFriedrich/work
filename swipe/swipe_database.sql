# 创建数据库 swipe
CREATE DATABASE	swipe DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# 使用数据库 swipe
USE swipe;

DROP TABLE IF EXISTS `SwipeRecord`;

# 创建数据表 SwipeRecord
CREATE TABLE `SwipeRecord` (
  `SEQ` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `deviceid` varchar(20) NOT NULL COMMENT '模块id',
  `deviceip` varchar(20) NOT NULL COMMENT '模块ip',
  `clientid` varchar(20) NOT NULL COMMENT '刷卡设备id',
  `clientip` varchar(20) NOT NULL COMMENT '刷卡设备ip',
  `result` tinyint(4) NOT NULL COMMENT '刷卡结果:0 刷卡成功,1 刷卡失败',
  `timestamp` varchar(20) NOT NULL COMMENT '时间戳',
  PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='刷卡信息表';

# 创建数据用于测试
INSERT INTO `SwipeRecord` VALUES ('1','SHTG1420151201000072','192.168.111.83','','49.65.203.223','0','2015/12/10 13:35:29');
INSERT INTO `SwipeRecord` VALUES ('2','SHTG1420151201000071','192.168.111.82','','49.65.203.223','0','2015/12/10 13:35:34');
INSERT INTO `SwipeRecord` VALUES ('3','SHTG1420151201000070','192.168.111.81','','49.65.203.223','0','2015/12/10 13:35:39');
INSERT INTO `SwipeRecord` VALUES ('4','SHTG1420151201000068','192.168.111.79','','49.65.203.223','0','2015/12/10 13:36:01');


DROP TABLE IF EXISTS `DeviceStatus`;
# 创建数据表 DeviceStatus
CREATE TABLE `DeviceStatus`(
  `SEQ` INTEGER(11) PRIMARY KEY AUTO_INCREMENT comment '序号',
  `deviceid` VARCHAR(20) NOT NULL comment '模块id',
  `deviceip` VARCHAR(20) NOT NULL comment '模块ip',
  `status` INTEGER(11) NOT NULL comment '模块状态:0 正常,1 异常',
  `timestamp` VARCHAR(20) NOT NULL comment '时间戳',
  `info` VARCHAR(200) NOT NULL comment '',
  `reserved` CHAR(8) NOT NULL comment ''
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='模块状态';

# 创建数据用于测试
INSERT INTO `DeviceStatus` VALUES('1','SHTG1420151201000072','192.168.111.83','0','2015/12/10 13:35:29','','');
INSERT INTO `DeviceStatus` VALUES('2','SHTG1420151201000071','192.168.111.82','0','2015/12/10 13:35:34','','');
INSERT INTO `DeviceStatus` VALUES('3','SHTG1420151201000070','192.168.111.81','0','2015/12/10 13:35:39','','');
INSERT INTO `DeviceStatus` VALUES('4','SHTG1420151201000068','192.168.111.79','0','2015/12/10 13:36:01','','');

COMMIT;