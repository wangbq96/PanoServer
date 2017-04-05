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
     * @api {POST} /comments
     * @apiGroup Comment
     * @apiDescription 发布评论
     * @apiParam {int} userId 用户ID
     * @apiParam {int} recordId 记录ID
     * @apiParam {String} content 评论内容
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": null
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
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
     * @api {POST} /comments/delete
     * @apiGroup Comment
     * @apiDescription 删除评论
     * @apiParam {int} userId 用户ID
     * @apiParam {int} commentId 记录ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": null
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": null
     *     }
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteComment(@RequestParam int userId,
                                @RequestParam int commentId) throws Exception {
        commentService.deleteComment(userId, commentId);
        return new RightMessage(null).toString();
    }

    /**
     * @api {GET} /comments/record/:recordId
     * @apiGroup Comment
     * @apiDescription 获取某个记录的评论
     * @apiParam {int} recordId 记录ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": null
     *     }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": false,
     *         "message": "Exception Name",
     *         "data": []
     *     }
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/comments/record/{recordId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getComments(@PathVariable int recordId) throws IOException {

        return new RightMessage(commentService.getComments(recordId)).toString();
    }
}
