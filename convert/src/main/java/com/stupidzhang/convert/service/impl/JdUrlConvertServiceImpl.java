package com.stupidzhang.convert.service.impl;

import com.stupidzhang.convert.service.UrlConvertService;
import com.stupidzhang.convert.service.strategy.JdUrlConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JdUrlConvertServiceImpl implements UrlConvertService {

    @Autowired
    private JdUrlConvertService jdUrlConvertService;

    @Override
    public String convert(String content) {
        return jdUrlConvertService.jdShortUrl(content);
    }

    @Override
    public Boolean support(String type) {
        return "jd".equals(type);
    }

}
