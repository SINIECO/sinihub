package com.sq.db.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.*;


/**
 * Created by ywj on 2015/12/18.
 */
@Entity
@Table(name = "t_projectpoint")
public class ProjectPoint extends AbstractEntity<Long>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    //链接Ip地址
    private String hospId;
    //端口号
    private Integer port;
    //数据库名称
    private String dbName;
    //用户名
    private String userName;
    //用户密码
    private String passWord;
    //测点位置描述
    private String pointName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        id = id;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    @Override
    public String toString() {
        return "ProjectPoint{" +
                "Id=" + id +
                ", hospId='" + hospId + '\'' +
                ", port=" + port +
                ", dbName='" + dbName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", pointName='" + pointName + '\'' +
                '}';
    }
}
