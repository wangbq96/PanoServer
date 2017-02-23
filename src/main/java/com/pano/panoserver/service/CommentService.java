package com.pano.panoserver.service;

import com.pano.panoserver.model.Comment;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by wangboquan on 17/2/17.
 */
public interface CommentService {
    void comment(Comment comment) throws IOException;
    void deleteComment(int userId, int commentId) throws Exception;
    List<Comment> getComments(int recordId) throws IOException;
}
