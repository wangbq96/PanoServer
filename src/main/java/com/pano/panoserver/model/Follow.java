package com.pano.panoserver.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by wangboquan on 2016/11/9.
 */
@Entity
public class Follow {
    @Id
    @NotNull
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private int followUserId;

    @Column(nullable = false)
    private int followerUserId;

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

    public int getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(int followUserId) {
        this.followUserId = followUserId;
    }

    public int getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(int followerUserId) {
        this.followerUserId = followerUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follow that = (Follow) o;

        if (id != that.id) return false;
        if (followUserId != that.followUserId) return false;
        if (followerUserId != that.followerUserId) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + followUserId;
        result = 31 * result + followerUserId;
        return result;
    }
}
