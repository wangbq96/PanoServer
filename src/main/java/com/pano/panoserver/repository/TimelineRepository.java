package com.pano.panoserver.repository;

import com.pano.panoserver.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 17/3/13.
 */
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    @Transactional
    Timeline findByUserIdAndRecordId(int userId, int recordId);
}
