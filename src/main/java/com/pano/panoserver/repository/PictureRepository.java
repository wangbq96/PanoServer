package com.pano.panoserver.repository;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wangboquan on 17/2/16.
 */
public interface PictureRepository {
    String save(MultipartFile file, String dir) throws Exception;
}
