package com.pano.panoserver.service;

import com.pano.panoserver.model.Record;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by wangboquan on 17/2/17.
 */
public interface RecordService {
    void publish(Record record, MultipartFile file) throws Exception;
    List<Record> getSomeoneRecords(int userId) throws IOException;
    List<Record> getTimeline(int userId, int earlyRecordId) throws IOException;
}
