package com.pano.panoserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Record;
import com.pano.panoserver.model.Timeline;
import com.pano.panoserver.model.User;
import com.pano.panoserver.repository.*;
import com.pano.panoserver.service.FollowService;
import com.pano.panoserver.service.RecordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by wangboquan on 17/2/17.
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private FollowRepository followRepository;

    public void publish(Record record, MultipartFile file) throws Exception {
        User user = userRepository.findOne(record.getUserId());
        String userDir = user.getDir();
        String picturePath = pictureRepository.save(file, userDir);

        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        record.setPicturePath(picturePath);
        recordRepository.saveAndFlush(record);
        // publishUpdateTimeline(user, record);
    }

    public List<Record> getSomeoneRecords(int userId) throws IOException {
        return recordRepository.findByUserId(userId);
    }

    public List<Record> getTimeline(int userId, int earlyRecordId) throws IOException {
        return recordRepository.getTimeline(userId, earlyRecordId);
    }

    @Async
    private void publishUpdateTimeline(User user, Record record) {
        List<Integer> fans = followRepository.getFollowerList(user.getId());
        for (int id :fans ){
            Timeline timeline = new Timeline();
            timeline.setUserId(id);
            timeline.setRecordId(record.getId());
            timeline.setPosterId(record.getUserId());
            timeline.setCreateTime(new Timestamp(System.currentTimeMillis()));
            timelineRepository.saveAndFlush(timeline);
        }
    }
}
