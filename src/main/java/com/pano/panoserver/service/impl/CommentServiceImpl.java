package com.pano.panoserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.exception.CannotDeleteException;
import com.pano.panoserver.exception.NotFoundException;
import com.pano.panoserver.model.Comment;
import com.pano.panoserver.repository.CommentRepository;
import com.pano.panoserver.repository.UserRepository;
import com.pano.panoserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangboquan on 17/2/17.
 */
@Service
public class CommentServiceImpl implements CommentService {
    private Comment comment;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void comment(Comment comment) throws IOException {
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteComment(int userId, int commentId) throws Exception {
        comment = commentRepository.findOne(commentId);
        if (comment == null) {
            throw new NotFoundException();
        }
        if (comment.getUserId() != userId) {
            throw new CannotDeleteException();
        }
        commentRepository.delete(commentId);
    }

    @Override
    public List<Comment> getComments(int recordId) throws IOException {
        return commentRepository.findByRecordId(recordId);

    }
}
