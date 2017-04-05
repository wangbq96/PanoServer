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
}
