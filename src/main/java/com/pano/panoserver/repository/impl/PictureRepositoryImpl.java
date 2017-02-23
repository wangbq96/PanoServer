package com.pano.panoserver.repository.impl;

import com.pano.panoserver.exception.ExistException;
import com.pano.panoserver.repository.PictureRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by wangboquan on 17/2/16.
 */
@Repository
public class PictureRepositoryImpl implements PictureRepository {

    @Value("${picture.path}")
    private String pictureRootPath;

    @Override
    public String save(MultipartFile file, String userDir) throws Exception {
        String fileName = file.getOriginalFilename();

        //handle filePath
        String filePath = pictureRootPath + "/" + userDir + "/";
        String relFilePath =  userDir+"/";
        String [] temp = fileName.split("\\.");
        String postfix=temp[temp.length-1];
        //rename fileName
        fileName = RandomStringUtils.randomAlphanumeric(11)+"."+postfix;

        File dir =new File(filePath);
        if (!dir.exists() && !dir.isDirectory())
            dir.mkdir();

        File pictureFile = new File(filePath, fileName);
        if(!pictureFile.exists()) {
            pictureFile.createNewFile();
            file.transferTo(pictureFile);
        } else {
            throw new ExistException();

        }
        return relFilePath + fileName;
    }
}
