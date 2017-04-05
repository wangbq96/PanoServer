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
     * @api {POST} /follow
     * @apiGroup Follow
     * @apiDescription 关注
     * @apiParam {int} userId 用户ID
     * @apiParam {int} followerUserId 被关注用户ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": null
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
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
     * @api {POST} /follow/delete
     * @apiGroup Follow
     * @apiDescription 取消关注
     * @apiParam {int} userId 用户ID
     * @apiParam {int} followerUserId 被关注用户ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": null
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/follow/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String unfollow(@RequestParam int userId,
                           @RequestParam int followerUserId) throws Exception {

        followService.unfollow(userId, followerUserId);
        return new RightMessage(null).toString();
    }
}
