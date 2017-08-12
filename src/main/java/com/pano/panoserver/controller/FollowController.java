package com.pano.panoserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Message;
import com.pano.panoserver.model.RightMessage;
import com.pano.panoserver.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangboquan on 2016/11/3.
 */

@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message follow(@RequestParam int userId,
                         @RequestParam int followerUserId) throws Exception {

        followService.follow(userId, followerUserId);
        return new RightMessage(null);

    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/follow/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message unfollow(@RequestParam int userId,
                           @RequestParam int followerUserId) throws Exception {

        followService.unfollow(userId, followerUserId);
        return new RightMessage(null);
    }
}
