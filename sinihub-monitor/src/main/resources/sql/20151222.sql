--连接信息表
CREATE TABLE `t_projectpoint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hospId` varchar(255) DEFAULT NULL,
  `port` bigint(20) DEFAULT NULL,
  `dbname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pointname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO t_projectpoint(hospId,port,dbname,username,password,pointname)
VALUES('218.94.66.122',3306,'gdmis','root','gdmis!@#$','南京光大');

INSERT INTO t_projectpoint(hospId,port,dbname,username,password,pointname)
VALUES('183.207.173.194',12306,'gov_rd_new','root','123456','如东环卫');

INSERT INTO t_projectpoint(hospId,port,dbname,username,password,pointname)
VALUES('58.241.142.98',12306,'snmis','root','123456','扬州泰达');
--测点表
CREATE TABLE `t_checkitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectpointId` bigint(20) DEFAULT NULL,
  `sysId` bigint(255) DEFAULT NULL,
  `sysname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_itid` (`projectpointId`),
  CONSTRAINT `fk_itid` FOREIGN KEY (`projectpointId`) REFERENCES `t_projectpoint` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO t_checkitem(projectpointId,sysId,sysname) VALUES(8,1,'DCS');
INSERT INTO t_checkitem(projectpointId,sysId,sysname) VALUES(8,3,'ECS');
INSERT INTO t_checkitem(projectpointId,sysId,sysname) VALUES(8,1,'DCS');
INSERT INTO t_checkitem(projectpointId,sysId,sysname) VALUES(8,4,'汽机');
INSERT INTO t_checkitem(projectpointId,sysId,sysname) VALUES(10,1,'DCS');
--检测结果表
CREATE TABLE `t_checkresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `checkitemId` bigint(20) DEFAULT NULL,
  `problemtype` int(11) DEFAULT NULL,
  `checktime` datetime DEFAULT NULL,
  `syncstatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ck_itid` (`checkitemId`),
  CONSTRAINT `ck_itid` FOREIGN KEY (`checkitemId`) REFERENCES `t_checkitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--邮箱发送记录
CREATE TABLE `t_emailreceiverecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `sendtime` datetime  DEFAULT NULL,
  `sendstatus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
