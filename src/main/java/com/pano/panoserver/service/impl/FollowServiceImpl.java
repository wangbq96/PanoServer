package com.pano.panoserver.service.impl;

import com.pano.panoserver.exception.FollowExistException;
import com.pano.panoserver.exception.FollowNotFoundException;
import com.pano.panoserver.exception.NotFoundException;
import com.pano.panoserver.model.Follow;
import com.pano.panoserver.repository.FollowRepository;
import com.pano.panoserver.repository.UserRepository;
import com.pano.panoserver.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;


/**
 * Created by wangboquan on 17/2/17.
 */
@Service
public class FollowServiceImpl implements FollowService {
    private Follow follow;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    public void follow(int userId, int followerUserId) throws Exception {
        if (followRepository.findByFollowUserIdAndFollowerUserId(userId, followerUserId) != null) {
            throw new FollowExistException();
        }
        follow = new Follow();
        follow.setFollowUserId(userId);
        follow.setFollowerUserId(followerUserId);
        follow.setCreateTime(new Timestamp(System.currentTimeMillis()));
        followRepository.saveAndFlush(follow);
    }

    public void unfollow(int userId, int unfollowUserId) throws Exception {
        follow = followRepository.findByFollowUserIdAndFollowerUserId(userId, unfollowUserId);
        if (follow != null) {
            followRepository.delete(follow);
        } else {
            throw new NotFoundException();
        }
    }
}
