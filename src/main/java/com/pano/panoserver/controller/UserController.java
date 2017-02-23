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
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param nickname
     * @param password
     * @param email
     * @param phone
     * @return 返回正常结果json数据
     * @throws Exception
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
     * 登录
     * @param username
     * @param password
     * @param type
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sessions/{type}", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @PathVariable(value = "type") String type) throws Exception {

        Token token = userService.login(username, password, type);
        return new RightMessage(token).toString();

    }

    /**
     * 登出
     * @param userId
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/sessions/{userId}", method = RequestMethod.DELETE)
    public String logout(@PathVariable(value = "userId") int userId) throws Exception {
        userService.logout(userId);
        return new RightMessage(null).toString();
    }

    /**
     * 搜索用户
     * @param keyword
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/search/{keyword}", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String searchUser(@PathVariable(value = "keyword") String keyword) throws Exception {

        List<User> list = userService.searchUser(keyword);
        return new RightMessage(list).toString();
    }

    /**
     * 获取某个用户
     * @param userId
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getUser(@PathVariable(value = "userId") int userId) throws Exception {

        User user = userService.getUser(userId);
        return new RightMessage(user).toString();
    }

    /**
     * 获取粉丝或关注列表
     * @param userId
     * @param type
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/follow/{type}/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getFollowUsers(@PathVariable(value = "userId") int userId,
                                @PathVariable(value = "type") String type) throws Exception {

        List<User> list = userService.getFollowUsers(userId, type);
        return new RightMessage(list).toString();
    }

    /**
     * 忘记密码
     * @param email
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/users/forget_password/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String forgetPassword(@RequestParam String email) throws Exception {
        userService.forgetPassword(email);
        return new RightMessage(null).toString();
    }

    /**
     * 忘记密码后检验密钥
     * @param email
     * @param key
     * @param password
     * @return 返回正常结果json数据
     * @throws Exception
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
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/users/password/{userId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public String updatePassword(@PathVariable int userId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword) throws Exception {
        userService.updatePassword(userId, oldPassword, newPassword);
        return new RightMessage(null).toString();
    }

    /**
     * 修改个人信息
     * @param userId
     * @param nickname
     * @param introduction
     * @param headPic
     * @return 返回正常结果json数据
     * @throws Exception
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
