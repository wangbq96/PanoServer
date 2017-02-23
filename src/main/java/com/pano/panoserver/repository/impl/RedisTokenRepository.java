package com.pano.panoserver.repository.impl;

import com.pano.panoserver.repository.TokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * Created by wangboquan on 17/2/7.
 */
@Repository
public class RedisTokenRepository implements TokenRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public String createToken(int userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        //String token = RandomStringUtils.randomAlphanumeric(10);

        //存储到redis并设置过期时间
        //redis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        redisTemplate.boundValueOps(userId).set(token);
        return token;
    }

    @Override
    public boolean checkToken(int userId, String userToken) {
        String token = (String) redisTemplate.boundValueOps(userId).get();
        if (token == null || !token.equals(userToken)) {
            return false;
        } else {
            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            //redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
            return true;
        }
    }

    @Override
    public void deleteToken(int userId) {
        redisTemplate.delete(userId);
    }
}
