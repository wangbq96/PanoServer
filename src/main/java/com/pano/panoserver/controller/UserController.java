package com.pano.panoserver.controller;

/**
 * Created by wangboquan on 2016/10/11.
 */
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.*;
import com.pano.panoserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController extends ExceptionHandlerController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message register(@RequestParam String nickname,
                                    @RequestParam String password,
                                    @RequestParam String email,
                                    @RequestParam String phone) throws Exception {

        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        userService.register(user);

        return new RightMessage(null);
    }

    @ResponseBody
    @RequestMapping(value = "/sessions/email", method = RequestMethod.POST)
    public Message loginByEmail(@RequestParam String username,
                               @RequestParam String password) throws Exception {

        Token token = userService.login(username, password, "email");

        return new RightMessage(token);
    }

    @ResponseBody
    @RequestMapping(value = "/sessions/phone", method = RequestMethod.POST)
    public Message loginByPhone(@RequestParam String username,
                               @RequestParam String password) throws Exception {

        Token token = userService.login(username, password, "phone");
        return new RightMessage(token);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/sessions/{userId}", method = RequestMethod.DELETE)
    public Message logout(@PathVariable(value = "userId") int userId) throws Exception {
        userService.logout(userId);
        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/search", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message searchUser(@RequestParam String keyword) throws Exception {
        List<User> list = userService.searchUser(keyword);
        return new RightMessage(list);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Message getUser(@PathVariable(value = "userId") int userId) throws Exception {

        User user = userService.getUser(userId);
        return new RightMessage(user);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/follow/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Message getFollowUsers(@PathVariable(value = "userId") int userId) throws Exception {

        List<User> list = userService.getFollowUsers(userId, "follow");
        return new RightMessage(list);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/fans/{userId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Message getFanUsers(@PathVariable(value = "userId") int userId) throws Exception {

        List<User> list = userService.getFollowUsers(userId, "fans");
        return new RightMessage(list);
    }

    @ResponseBody
    @RequestMapping(value = "/users/forget_password/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Message forgetPassword(@RequestParam String email) throws Exception {
        userService.forgetPassword(email);
        return new RightMessage(null);
    }

    @ResponseBody
    @RequestMapping(value = "/users/reset_password/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Message resetPassword(@RequestParam String email,
                                @RequestParam String key,
                                @RequestParam String password) throws Exception {
        userService.resetPassword(email, key, password);
        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Message updatePassword(@RequestParam int userId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword) throws Exception {
        userService.updatePassword(userId, oldPassword, newPassword);
        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/users/updateInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Message updateUserInfo(@RequestParam int userId,
                                 @RequestParam String nickname,
                                 @RequestParam String introduction,
                                 @RequestParam MultipartFile headPic) throws Exception {

        userService.updateUserInfo(userId, nickname, introduction, headPic);
        return new RightMessage(null);
    }
}
