package com.stupidzhang.cps.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunClient {

    @Bean
    public OSS oss() {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tK5VBJzNrwNaCDGGcYw";
        String accessKeySecret = "m0OGreh1N6GT5kPcdc2znTsKxIEy23";
        // 创建OSSClient实例  使用单
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }


}
