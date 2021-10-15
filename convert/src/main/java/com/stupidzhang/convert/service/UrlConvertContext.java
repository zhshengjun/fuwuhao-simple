package com.stupidzhang.convert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class UrlConvertContext {

    @Autowired
    private List<UrlConvertService> urlConvertServiceList;

    public String convertUrl(String type, String content) {
        for (UrlConvertService urlConvertService : urlConvertServiceList) {
            if (urlConvertService.support(type)) {
                return urlConvertService.convert(content);
            }
        }
        return "";
    }
}
