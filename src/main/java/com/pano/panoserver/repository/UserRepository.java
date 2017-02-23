package com.pano.panoserver.repository;

import com.pano.panoserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 2016/10/11.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNickname(String nickname);

    User findByEmailAndPassword(String email, String password);

    User findByPhoneAndPassword(String phone, String password);

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User us set us.token=:qToken where us.id=:qId")
    void updateToken(@Param("qId") int id, @Param("qToken") String token);

    @Modifying
    @Transactional
    @Query("update User us set us.password=:qPassword where us.id=:qId")
    void updatePassword(@Param("qId") int id, @Param("qPassword") String password);

    @Modifying
    @Transactional
    @Query("update User us set us.nickname=:qNickname, us.introduction=:qIntroduction, us.headPic=:qHeadPic " +
            "where us.id=:qId")
    void updateUserInfo(@Param("qId") int id,
                        @Param("qNickname") String nickname,
                        @Param("qIntroduction") String introduction,
                        @Param("qHeadPic") byte [] headPic);
}
