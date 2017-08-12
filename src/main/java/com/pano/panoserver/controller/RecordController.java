package com.pano.panoserver.controller;

import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.model.Message;
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

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/records", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Message publish(@RequestParam int userId,
                           @RequestParam String content,
                           @RequestParam(required = false) String lon,
                           @RequestParam(required = false) String lan,
                           @RequestParam MultipartFile file) throws Exception {
        Record record = new Record();
        record.setUserId(userId);
        record.setContent(content);

        recordService.publish(record, file);
        return new RightMessage(null);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/records/user/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public Message getSomeoneRecords(@PathVariable int userId) throws IOException {
        return new RightMessage(recordService.getSomeoneRecords(userId));
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/timeline/{userId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public Message getFocusRecords(@PathVariable int userId,
                                  @RequestParam int earlyRecordId) throws IOException {
        return new RightMessage(recordService.getTimeline(userId, earlyRecordId));
    }
}
