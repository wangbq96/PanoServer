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

    /**
     * 关注
     * @param userId
     * @param followerUserId
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String follow(@RequestParam int userId,
                         @RequestParam int followerUserId) throws Exception {

        followService.follow(userId, followerUserId);
        return new RightMessage(null).toString();

    }

    /**
     * 取消关注
     * @param userId
     * @param unfollowUserId
     * @return
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/unfollow", method = RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    public String unfollow(@RequestParam int userId,
                           @RequestParam int unfollowUserId) throws Exception {

        followService.unfollow(userId, unfollowUserId);
        return new RightMessage(null).toString();
    }
}
