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
    RecordService recordService;

    /**
     * 发布
     * @param request
     * @param userId
     * @param content
     * @param lon
     * @param lan
     * @param file
     * @return 返回正常结果json数据
     * @throws Exception
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/records", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String publish(HttpServletRequest request,
                          @RequestParam int userId,
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
     * 获取某人的全部pano
     * @param userId
     * @return 返回正常结果json数据
     * @throws IOException
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/records/user/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getSomeoneRecords(@PathVariable int userId) throws IOException {
        return new RightMessage(recordService.getSomeoneRecords(userId)).toString();
    }

    /**
     * 获取timeline
     * @param userId
     * @param earlyRecordId
     * @return 返回正常结果json数据
     * @throws IOException
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/timeline/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String getFocusRecords(@PathVariable int userId,
                                  @RequestParam int earlyRecordId) throws IOException {
        return new RightMessage(recordService.getTimeline(userId, earlyRecordId)).toString();
    }
}
