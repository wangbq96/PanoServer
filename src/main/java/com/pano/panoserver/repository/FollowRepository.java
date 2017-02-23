package com.pano.panoserver.repository;

import com.pano.panoserver.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 2016/11/3.
 */
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Transactional
    Follow findByFollowUserIdAndFollowerUserId(int followUserId, int followerUserId);

    @Transactional
    @Query("select f.followerUserId from Follow f where f.followUserId=:qId")
    List<Integer> getFollowList(@Param("qId") int userId);

    @Transactional
    @Query("select f.followUserId from Follow f where f.followerUserId=:qId")
    List<Integer> getFollowerList(@Param("qId") int userId);
}
