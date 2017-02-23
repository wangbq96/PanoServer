package com.pano.panoserver.service;

import java.io.IOException;

/**
 * Created by wangboquan on 17/2/17.
 */
public interface FollowService {
    void follow(int userId, int followerUserId) throws Exception;
    void unfollow(int userId, int unfollowUserId) throws Exception;
}
