package com.pano.panoserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by wangboquan on 17/3/13.
 */
@Entity
public class Timeline {
    @Id
    @NotNull
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private int recordId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int posterId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }
}
