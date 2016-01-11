package com.sq.db.domain;

import com.sq.entity.AbstractEntity;
import com.sq.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Created by ywj on 2016/1/8.
 */
@Entity
@Table(name = "t_checkport")
public class CheckPort extends AbstractEntity<Long> {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(cascade= CascadeType.REFRESH, fetch= FetchType.LAZY)
    @JoinColumn(name="projectId")
    @org.hibernate.annotations.ForeignKey(name="fk_portid")
    @NotFound(action= NotFoundAction.IGNORE)
    private ProjectPoint projectPoint;
    private Integer port;
    @Override
    public Long getId() {
        return Id;
    }

    @Override
    public void setId(Long aLong) {

    }

    public ProjectPoint getProjectPoint() {
        return projectPoint;
    }

    public void setProjectPoint(ProjectPoint projectPoint) {
        this.projectPoint = projectPoint;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
