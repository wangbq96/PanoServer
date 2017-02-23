package com.pano.panoserver.repository;

import com.pano.panoserver.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangboquan on 2016/10/12.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    List<Comment> findByRecordId(int recordId);
}
