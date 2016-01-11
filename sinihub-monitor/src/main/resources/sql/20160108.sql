--监测项目点的所有端口表
CREATE TABLE `t_checkport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectId` bigint(20) DEFAULT NULL,
  `port` bigint(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_portid` (`projectId`),
  CONSTRAINT `fk_portid` FOREIGN KEY (`projectId`) REFERENCES `t_projectpoint` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
--数据插入
    --扬州泰达
    INSERT into t_checkport(projectId,port) VALUES(8,12306);
    INSERT into t_checkport(projectId,port) VALUES(8,1313);
    INSERT into t_checkport(projectId,port) VALUES(8,1212);
    INSERT into t_checkport(projectId,port) VALUES(8,3389);
    --南京光大
    INSERT into t_checkport(projectId,port) VALUES(9,8989);
    INSERT into t_checkport(projectId,port) VALUES(9,3306);
    INSERT into t_checkport(projectId,port) VALUES(9,3451);
    INSERT into t_checkport(projectId,port) VALUES(9,3452);
    INSERT into t_checkport(projectId,port) VALUES(9,3453);
    INSERT into t_checkport(projectId,port) VALUES(9,3454);
    INSERT into t_checkport(projectId,port) VALUES(9,3455);
    INSERT into t_checkport(projectId,port) VALUES(9,3456);
    INSERT into t_checkport(projectId,port) VALUES(9,3463);
    --如东环卫
    INSERT into t_checkport(projectId,port) VALUES(10,23);
    INSERT into t_checkport(projectId,port) VALUES(10,8989);
    INSERT into t_checkport(projectId,port) VALUES(10,12306);
    INSERT into t_checkport(projectId,port) VALUES(10,24022);
    INSERT into t_checkport(projectId,port) VALUES(10,24122);
    INSERT into t_checkport(projectId,port) VALUES(10,3389);


