package com.pano.panoserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.exception.*;
import com.pano.panoserver.model.PasswordReset;
import com.pano.panoserver.model.Token;
import com.pano.panoserver.model.User;
import com.pano.panoserver.repository.FollowRepository;
import com.pano.panoserver.repository.PasswordResetRespository;
import com.pano.panoserver.repository.TokenRepository;
import com.pano.panoserver.repository.UserRepository;
import com.pano.panoserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by wangboquan on 17/2/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("{spring.mail.username}")
    private String serverEmail;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordResetRespository passwordResetRespository;
    @Autowired
    JavaMailSender mailSender;

    // ok
    public void register(User user) throws Exception{
        String password = user.getPassword();
        String email = user.getEmail();

        user.setPassword(md5(password));
        user.setDir(md5(email));

        user.setCreateTime(new Timestamp(System.currentTimeMillis()));

        try {
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExistException();
        }

    }

    // ok
    public Token login(String username, String password, String type) throws Exception {
        password = md5(password);
        if (type.equals("email")) {
            user = userRepository.findByEmailAndPassword(username, password);
        } else if (type.equals("phone")) {
            user = userRepository.findByPhoneAndPassword(username, password);
        } else {
            throw new LoginTypeException();
        }
        if(user != null ) {
            int userId = user.getId();
            String token = tokenRepository.createToken(userId);
            return new Token(userId, token);
        } else {
            throw new WrongPasswordException();
        }
    }

    // ok
    public void logout(int userId) throws Exception {
        try {
            tokenRepository.deleteToken(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    // ok
    public List<User> searchUser(String keyword) {
        return userRepository.findByNickname(keyword);
    }

    // ok
    public User getUser(int userId) {
        return userRepository.findOne(userId);
    }

    // ok
    public List<User> getFollowUsers(int userId, String type) {
        List<Integer> userIdList;
        if (type.equals("follow")) {
            userIdList = followRepository.getFollowList(userId);
        }
        else if (type.equals("fans")) {
            userIdList = followRepository.getFollowerList(userId);
        }
        else {
            return null;
        }
        if (userIdList.size() != 0) {
            List<User> list = new LinkedList<User>();
            for (int focusUserId : userIdList) {
                list.add(userRepository.findOne(focusUserId));
            }
            return list;
        } else {
            return null;
        }
    }

    @Override
    public void forgetPassword(String email) throws Exception {
        if (userRepository.findByEmail(email) == null) {
            throw new NotFoundException();
        }
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(email);
        passwordReset.setKey(UUID.randomUUID().toString());

        passwordResetRespository.saveAndFlush(passwordReset);


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(serverEmail);
        message.setTo(email);
        message.setSubject("找回密码");
        message.setText("验证码为: " + passwordReset.getKey());
        mailSender.send(mimeMessage);
        //send email
    }

    @Override
    public void resetPassword(String email, String key, String password) throws Exception {
        PasswordReset passwordReset = passwordResetRespository.findByEmail(email);
        if (passwordReset == null) {
            throw new NotFoundException();
        }
        if ( !passwordReset.getKey().equals(key)) {
            throw new UnauthorizedException();
        }
        User user = userRepository.findByEmail(email);
        user.setPassword(md5(password));
        userRepository.updatePassword(user.getId(), user.getPassword());
    }

    @Override
    public void updatePassword(int userId, String oldPassword, String newPassword) throws Exception {
        oldPassword = md5(oldPassword);
        User user = userRepository.findOne(userId);

        if ( !user.getPassword().equals(oldPassword)) {
            throw new UnauthorizedException();
        }
        newPassword = md5(newPassword);
        userRepository.updatePassword(userId, newPassword);
    }

    public void updateUserInfo(int userId, String nickname, String introduction, MultipartFile headPic) throws Exception {
        User user = userRepository.findOne(userId);

        InputStream fileStream = headPic.getInputStream();
        byte [] headPicByte = iuput2byte(fileStream);
        fileStream.close();
        userRepository.updateUserInfo(userId, nickname, introduction, headPicByte);
    }

    private byte [] iuput2byte(InputStream in) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte [] buff = new byte[1024 * 1024];
        int rc = 0;
        while ((rc = in.read(buff, 0, 1024 * 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte [] result = swapStream.toByteArray();
        return result;
    }

    private String md5(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(str.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1, md.digest()).toString(16);
    }
}
