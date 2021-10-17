package com.stupidzhang.cps.service;

import com.aliyun.oss.OSS;
import com.stupidzhang.cps.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AliYunUploadService {

    @Autowired
    private OSS oss;

    public String upload(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            String fileName = UUID.randomUUID() + ".png";
            // 上传文件。
            oss.putObject(Constants.ALIYUN_OSS_BUCKETNAME, Constants.ALIYUN_OSS_UPLOAD_PATH + fileName, inputStream);
            return Constants.ALIYUN_OSS_DOMAIN + Constants.ALIYUN_OSS_UPLOAD_PATH + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
