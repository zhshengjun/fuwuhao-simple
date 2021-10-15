package com.stupidzhang.convert.service.impl;

import com.stupidzhang.convert.service.UrlConvertService;
import com.stupidzhang.convert.service.strategy.DaTaoKeUrlConvertService;
import com.stupidzhang.convert.service.strategy.DingDanXiaUrlConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class TbUrlConvertServiceImpl implements UrlConvertService {

    @Value("${convert.dataSource:ddx}")
    private String dataSource;

    @Autowired
    private DingDanXiaUrlConvertService dingDanXiaUrlConvertService;

    @Autowired
    private DaTaoKeUrlConvertService daTaoKeUrlConvertService;

    @Override
    public String convert(String content) {
        if ("ddx".equals(dataSource)) {
            return dingDanXiaUrlConvertService.convert(content);
        }
        return daTaoKeUrlConvertService.taoBaoTwdToTwd(content);

    }

    @Override
    public Boolean support(String type) {
        return "ddx".equals(type);
    }

}
