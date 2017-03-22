package com.pano.panoserver.controller;

/**
 * Created by wangboquan on 2016/10/11.
 */
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Message;
import com.pano.panoserver.model.RightMessage;
import com.pano.panoserver.model.Token;
import com.pano.panoserver.model.User;
import com.pano.panoserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController extends ExceptionHandlerController {
    @Autowired
    private UserService userService;

    /**
     * @api {POST} /users
     * @apiGroup User
     * @apiDescription 注册
     * @apiParam {String} nickname 昵称
     * @apiParam {String} password 密码
     * @apiParam {String} email 邮箱
     * @apiParam {String} phone 手机号
     * @apiSuccess (200) {String}
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
    @ResponseBody
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String register(@RequestParam String nickname,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone) throws Exception {

        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        userService.register(user);

        return new RightMessage(null).toString();
    }

    /**
     * @api {POST} /sessions/email
     * @apiGroup User
     * @apiDescription 使用email登录
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             "id": "user_id",
     *             "token": "token_str"
     *         }
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
     */
    @ResponseBody
    @RequestMapping(value = "/sessions/email", method = RequestMethod.POST)
    public String loginByEmail(@RequestParam String username,
                               @RequestParam String password) throws Exception {

        Token token = userService.login(username, password, "email");
        return new RightMessage(token).toString();

    }

    /**
     * @api {POST} /sessions/phone
     * @apiGroup User
     * @apiDescription 使用phone登录
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             "id": "user_id",
     *             "token": "token_str"
     *         }
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
     */
    @ResponseBody
    @RequestMapping(value = "/sessions/phone", method = RequestMethod.POST)
    public String loginByPhone(@RequestParam String username,
                               @RequestParam String password) throws Exception {

        Token token = userService.login(username, password, "email");
        return new RightMessage(token).toString();

    }

    /**
     * @api {DELETE} /sessions/:userId
     * @apiGroup User
     * @apiDescription 登出
     * @apiParam {int} userId 用户ID
     * @apiSuccess (200) {String}
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
    @RequestMapping(value = "/sessions/{userId}", method = RequestMethod.DELETE)
    public String logout(@PathVariable(value = "userId") int userId) throws Exception {
        userService.logout(userId);
        return new RightMessage(null).toString();
    }

    /**
     * @api {POST} /users/search/:keyword
     * @apiGroup User
     * @apiDescription 搜索用户
     * @apiParam {String} keyword 关键字
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             []
     *         }
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
    @RequestMapping(value = "/users/search/{keyword}", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String searchUser(@PathVariable(value = "keyword") String keyword) throws Exception {
        List<User> list = userService.searchUser(keyword);
        return new RightMessage(list).toString();
    }

    /**
     * @api {GET} /users/:userId
     * @apiGroup User
     * @apiDescription 获取某个用户的详细信息
     * @apiParam {int} userId 其他用户ID
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             user info
     *         }
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
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getUser(@PathVariable(value = "userId") int userId) throws Exception {

        User user = userService.getUser(userId);
        return new RightMessage(user).toString();
    }

    /**
     * @api {GET} /users/follow/:userId
     * @apiGroup User
     * @apiDescription 获取关注列表
     * @apiParam {int} userId 本用户ID
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             []
     *         }
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
    @RequestMapping(value = "/users/follow/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getFollowUsers(@PathVariable(value = "userId") int userId) throws Exception {

        List<User> list = userService.getFollowUsers(userId, "follow");
        return new RightMessage(list).toString();
    }

    /**
     * @api {GET} /users/fans/:userId
     * @apiGroup User
     * @apiDescription 获取粉丝列表
     * @apiParam {int} userId 本用户ID
     * @apiSuccess (200) {String}
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": {
     *             []
     *         }
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
    @RequestMapping(value = "/users/fans/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getFanUsers(@PathVariable(value = "userId") int userId) throws Exception {

        List<User> list = userService.getFollowUsers(userId, "fans");
        return new RightMessage(list).toString();
    }

    /**
     * @api {POST} /users/forget_password
     * @apiGroup User
     * @apiDescription 发送忘记密码的邮件
     * @apiParam {String} email 用户email
     * @apiSuccess (200) {String}
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
    @ResponseBody
    @RequestMapping(value = "/users/forget_password/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String forgetPassword(@RequestParam String email) throws Exception {
        userService.forgetPassword(email);
        return new RightMessage(null).toString();
    }

    /**
     * @api {POST} /users/reset_password
     * @apiGroup User
     * @apiDescription 重置密码
     * @apiParam {String} email 用户email
     * @apiParam {String} key 邮件中的关键字
     * @apiParam {String} password 新密码
     * @apiSuccess (200) {String}
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
    @ResponseBody
    @RequestMapping(value = "/users/reset_password/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String key,
                                @RequestParam String password) throws Exception {
        userService.resetPassword(email, key, password);
        return new RightMessage(null).toString();
    }

    /**
     * @api {PUT} /users/password/:userId
     * @apiGroup User
     * @apiDescription 修改密码
     * @apiParam {int} userId 用户ID
     * @apiParam {String} oldPassword 旧密码
     * @apiParam {String} newPassword 新密码
     * @apiSuccess (200) {String}
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
    @RequestMapping(value = "/users/password/{userId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public String updatePassword(@PathVariable int userId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword) throws Exception {
        userService.updatePassword(userId, oldPassword, newPassword);
        return new RightMessage(null).toString();
    }

    /**
     * @api {PUT} /users/password/:userId
     * @apiGroup User
     * @apiDescription 修改个人信息
     * @apiParam {int} userId 用户ID
     * @apiParam {String} nickname 昵称
     * @apiParam {String} introduction 简介
     * @apiParam {MultipartFile} headPic 头像
     * @apiSuccess (200) {String}
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
    @RequestMapping(value = "/users/info/{userId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public String updateUserInfo(@PathVariable int userId,
                                 @RequestParam String nickname,
                                 @RequestParam String introduction,
                                 @RequestParam MultipartFile headPic) throws Exception {

        userService.updateUserInfo(userId, nickname, introduction, headPic);
        return new RightMessage(null).toString();
    }
}
