package com.sq.db.domain;

import com.sq.entity.AbstractEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
/**
 * Created by ywj on 2015/12/18.
 */
@Entity
@Table(name = "t_checkitem")
public class CheckItem extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(cascade= CascadeType.REFRESH, fetch= FetchType.LAZY)
    @JoinColumn(name="projectpointId")
    @org.hibernate.annotations.ForeignKey(name="fk_itid")
    @NotFound(action= NotFoundAction.IGNORE)
    private ProjectPoint projectPoint;

    private Integer sysId;

    private String sysName;

    public CheckItem() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public void setProjectPoint(ProjectPoint projectPoint) {
        this.projectPoint = projectPoint;
    }

    public ProjectPoint getProjectPoint() {
        return projectPoint;
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "Id=" + Id +
                ", projectPoint=" + projectPoint +
                ", sysId=" + sysId +
                ", sysName='" + sysName + '\'' +
                '}';
    }
}