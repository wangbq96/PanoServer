package com.pano.panoserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Comment;
import com.pano.panoserver.model.Message;
import com.pano.panoserver.model.RightMessage;
import com.pano.panoserver.repository.CommentRepository;
import com.pano.panoserver.repository.UserRepository;
import com.pano.panoserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangboquan on 2016/10/12.
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message comment(@RequestParam String content,
                          @RequestParam int recordId,
                          @RequestParam int userId) throws IOException {

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setRecordId(recordId);

        commentService.comment(comment);

        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message deleteComment(@RequestParam int userId,
                                @RequestParam int commentId) throws Exception {
        commentService.deleteComment(userId, commentId);

        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/record/{recordId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public Message getComments(@PathVariable int recordId) throws IOException {

        return new RightMessage(commentService.getComments(recordId));
    }
}
