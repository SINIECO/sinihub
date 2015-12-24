-- ----------------------------
-- Table structure for 't_mesuringpoint'
-- ----------------------------
DROP TABLE IF EXISTS 't_mesuringpoint';
CREATE TABLE 't_mesuringpoint' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'sourceCode' varchar(255),
  'pointName' varchar(255),
  'sysCode' varchar(255),
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for 't_OriginalData'
-- ----------------------------
DROP TABLE IF EXISTS 't_OriginalData';
CREATE TABLE 't_OriginalData' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'itemCode' varchar(255),
  'itemValue' varchar(255),
  'instanceTime' dateTime,
  'sysCode'  varchar(64),
  'quality'  TINYINT,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for 't_AccessSystem'
-- ----------------------------
DROP TABLE IF EXISTS 't_AccessSystem';
CREATE TABLE 't_AccessSystem' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'sysCode' varchar(255),
  'sysName' varchar(255),
  'description' varchar(255),
  'protocalType' int,
  'syncCronExp'  varchar(255),
  'isSync' TINYINT,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for 't_JdbcProtocal'
-- ----------------------------
DROP TABLE IF EXISTS 't_JdbcProtocal';
CREATE TABLE 't_JdbcProtocal' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'sysCode' varchar(255),
  'jdbcDriverType' int,
  'hostIp' varchar(255),
  'port' varchar(255),
  'dbname'  varchar(255),
  'connecturl' varchar(255),
  'username' varchar(255),
  'password' varchar(255),
  'selectSql'  varchar(255),
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for 't_OpcProtocal'
-- ----------------------------
DROP TABLE IF EXISTS 't_OpcProtocal';
CREATE TABLE 't_OpcProtocal' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'sysCode' varchar(255),
  'opcDriverType' int,
  'hostIp' varchar(255),
  'domain' int,
  'userName'  varchar(255),
  'password' varchar(255),
  'clsid' varchar(255),
  'progid'  varchar(255),
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for 't_UdpProtocal'
-- ----------------------------
DROP TABLE IF EXISTS 't_UdpProtocal';
CREATE TABLE 't_UdpProtocal' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'sysCode' varchar(255),
  'hostIp' varchar(255),
  'port'  varchar(255),
  'udpUpdateRate' int,
  'syncRate' int,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_trade`
-- ----------------------------
DROP TABLE IF EXISTS `t_trade`;
CREATE TABLE `t_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lsh` varchar(20) DEFAULT NULL,
  `carNo` varchar(20) DEFAULT NULL,
  `proCode` varchar(11) DEFAULT NULL,
  `sourceArea` varchar(11) DEFAULT NULL,
  `firstWeightTime` varchar(200) DEFAULT NULL,
  `secondWeightTime` varchar(200) DEFAULT NULL,
  `gross` varchar(11) DEFAULT NULL,
  `tare` varchar(11) DEFAULT NULL,
  `net` varchar(11) DEFAULT NULL,
  `operator` varchar(11) DEFAULT NULL,
  `statDateNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

