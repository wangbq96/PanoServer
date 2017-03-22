package com.pano.panoserver.service.impl;

import com.pano.panoserver.exception.ExistException;
import com.pano.panoserver.exception.NotFoundException;
import com.pano.panoserver.model.Follow;
import com.pano.panoserver.model.Record;
import com.pano.panoserver.model.Timeline;
import com.pano.panoserver.repository.FollowRepository;
import com.pano.panoserver.repository.RecordRepository;
import com.pano.panoserver.repository.TimelineRepository;
import com.pano.panoserver.repository.UserRepository;
import com.pano.panoserver.service.FollowService;
import com.pano.panoserver.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by wangboquan on 17/2/17.
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private RecordRepository recordRepository;

    public void follow(int userId, int followerUserId) throws Exception {
        if (followRepository.findByFollowUserIdAndFollowerUserId(userId, followerUserId) != null) {
            throw new ExistException();
        }
        Follow follow = new Follow();
        follow.setFollowUserId(userId);
        follow.setFollowerUserId(followerUserId);
        follow.setCreateTime(new Timestamp(System.currentTimeMillis()));
        followRepository.saveAndFlush(follow);
    }

    public void unfollow(int userId, int unfollowUserId) throws Exception {
        Follow follow = followRepository.findByFollowUserIdAndFollowerUserId(userId, unfollowUserId);
        if (follow != null) {
            followRepository.delete(follow);
        } else {
            throw new NotFoundException();
        }
    }

    @Async
    private void followUpdateTimeline(int userId, int followerUserId) {
        List<Record> list = recordRepository.findByUserId(followerUserId);
        for (Record record : list) {
            Timeline timeline = new Timeline();
            timeline.setUserId(userId);
            timeline.setRecordId(record.getId());
            timeline.setPosterId(record.getUserId());
            timeline.setCreateTime(new Timestamp(System.currentTimeMillis()));
            timelineRepository.saveAndFlush(timeline);
        }
    }

    @Async
    private void unfollowUpdateTimeline(int userId, int unfollowUserId) {
        List<Record> list = recordRepository.findByUserId(unfollowUserId);
        for (Record record : list) {
            Timeline timeline = timelineRepository.findByUserIdAndRecordId(userId, record.getId());
            timelineRepository.delete(timeline);
        }

    }
}
