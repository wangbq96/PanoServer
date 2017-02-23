package com.pano.panoserver.repository;

/**
 * Created by wangboquan on 17/1/25.
 */
public interface TokenRepository  {
    String createToken(int userId);
    boolean checkToken(int userId, String userToken);
    void deleteToken(int userId);
}
