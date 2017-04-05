package com.pano.panoserver.repository;

import com.pano.panoserver.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 17/3/13.
 */
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    @Transactional
    @Query(value = "select record_id from timeline where timeline.user_id = ?1 and record_id < ?2 order by record_id desc limit 20", nativeQuery = true)
    List<Integer> getTimeline(int userId, int earlyRecordId);

    @Transactional
    @Query(value = "select record_id from timeline where timeline.user_id = ?1 order by record_id desc limit 20", nativeQuery = true)
    List<Integer> getTimelineFirst(int userId);

    @Transactional
    Timeline findByUserIdAndRecordId(int userId, int recordId);
}
