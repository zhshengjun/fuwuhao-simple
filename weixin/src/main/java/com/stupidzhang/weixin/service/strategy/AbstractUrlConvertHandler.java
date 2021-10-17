package com.stupidzhang.weixin.service.strategy;

import com.stupidzhang.convert.service.UrlConvertContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractUrlConvertHandler extends AbstractionHandler {


    @Autowired
    private UrlConvertContext urlConvertContext;

    @Override
    public String handle(String openId, String content) {
        String dataSource = dataSource();
        log.warn("转换平台为：{}",  dataSource);
        return urlConvertContext.convertUrl(dataSource, content);
    }

    protected abstract String dataSource();
}
