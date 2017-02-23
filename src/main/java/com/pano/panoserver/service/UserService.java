package com.pano.panoserver.service;

import com.pano.panoserver.model.Token;
import com.pano.panoserver.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by wangboquan on 17/2/17.
 */
public interface UserService {
    void register(User user) throws Exception;
    Token login(String username, String password, String type) throws Exception;
    void logout(int userId) throws Exception;
    List<User> searchUser(String keyword);
    User getUser(int userId) throws Exception;
    List<User> getFollowUsers(int userId, String type) throws Exception;
    void forgetPassword(String email) throws Exception;
    void resetPassword(String email, String key, String password) throws Exception;
    void updatePassword(int userId, String oldPassword, String newPassword) throws Exception;
    void updateUserInfo(int userId, String nickname, String introduction, MultipartFile headPic) throws Exception;
}
