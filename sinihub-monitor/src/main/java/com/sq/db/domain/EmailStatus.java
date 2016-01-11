package com.sq.db.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.*;

/**
 * Created by ywj on 2016/1/5.
 */
@Entity
@Table(name = "t_ctrlemail")
public class EmailStatus extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private Long checkItemId;
    private Integer status;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Long checkItemId) {
        this.checkItemId = checkItemId;
    }
}
