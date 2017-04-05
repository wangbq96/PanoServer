package com.pano.panoserver.controller;

import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Record;
import com.pano.panoserver.model.RightMessage;
import com.pano.panoserver.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by wangboquan on 2016/10/18.
 */
@RestController
public class RecordController extends ExceptionHandlerController {
    @Autowired
    private RecordService recordService;

    /**
     * @api {POST} /records
     * @apiGroup Record
     * @apiDescription 发布pano
     * @apiParam {int} userId 用户ID
     * @apiParam {String} content 内容
     * @apiParam {String} lon 经度 nullable
     * @apiParam {String} lan 纬度 nullable
     * @apiParam {MultipartFile} file 图片文件
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
    @RequestMapping(value = "/records", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String publish(@RequestParam int userId,
                          @RequestParam String content,
                          @RequestParam(required = false) String lon,
                          @RequestParam(required = false) String lan,
                          @RequestParam MultipartFile file) throws Exception {
        Record record = new Record();
        record.setUserId(userId);
        record.setContent(content);

        recordService.publish(record, file);
        return new RightMessage(null).toString();
    }

    /**
     * @api {GET} /records/user/:userId
     * @apiGroup Record
     * @apiDescription 获取某个用户所有的记录
     * @apiParam {int} userId 用户ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": []
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
    @RequestMapping(value = "/records/user/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getSomeoneRecords(@PathVariable int userId) throws IOException {
        return new RightMessage(recordService.getSomeoneRecords(userId)).toString();
    }

    /**
     * @api {GET} /timeline/:userId
     * @apiGroup Record
     * @apiDescription 获取timeline
     * @apiParam {int} userId 用户ID
     * @apiSuccess (200) {String} message
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "result": true,
     *         "message": null,
     *         "data": []
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
    @RequestMapping(value = "/timeline/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getFocusRecords(@PathVariable int userId,
                                  @RequestParam int earlyRecordId) throws IOException {
        return new RightMessage(recordService.getTimeline(userId, earlyRecordId)).toString();
    }
}
