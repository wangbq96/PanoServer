package com.pano.panoserver.repository;

import com.pano.panoserver.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 2016/10/12.
 */
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Transactional
    List<Record> findByUserId(int userId);

    @Transactional
    @Query(value = "select * from record where record.user_id in (select focused_user_id from focus where focused_user_id=?1) and record.id > ?2 order by create_time desc limit 20", nativeQuery = true)
    List<Record> getTimeline(int userId, int earlyRecordId);

    /*
        @Transactional
    @Query("select f.focusingUserId from Focus f where f.focusedUserId=:qId")
    List<Integer> getFansList(@Param("qId") int userId);
     */
}
