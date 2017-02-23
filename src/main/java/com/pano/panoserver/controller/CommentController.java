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

    /**
     * 评论
     * @param content
     * @param recordId
     * @param userId
     * @return 返回正常结果json数据
     * @throws IOException
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String comment(@RequestParam String content,
                          @RequestParam int recordId,
                          @RequestParam int userId) throws IOException {

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setRecordId(recordId);

        commentService.comment(comment);

        return new RightMessage(null).toString();
    }

    /**
     * 删除评论
     * @param userId
     * @param commentId
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    public String deleteComment(@RequestParam int userId,
                                @PathVariable int commentId) throws Exception {

        commentService.deleteComment(userId, commentId);
        return new RightMessage(null).toString();
    }

    /**
     * 获取某个pano的评论
     * @param recordId
     * @return 返回正常结果json数据
     * @throws IOException
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/record/{recordId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getComments(@PathVariable int recordId) throws IOException {

        return new RightMessage(commentService.getComments(recordId)).toString();
    }
}
