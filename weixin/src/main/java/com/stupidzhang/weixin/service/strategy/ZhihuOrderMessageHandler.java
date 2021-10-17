package com.stupidzhang.weixin.service.strategy;

import com.stupidzhang.weixin.contants.MessageContants;
import com.stupidzhang.weixin.service.JinFenOrderNotice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZhihuOrderMessageHandler extends AbstractionHandler {

    @Autowired
    private JinFenOrderNotice jinFenOrderNotice;

    @Override
    public int order() {
        return 5;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.containsAny(content, "知乎");
    }

    @Override
    public String handle(String openId, String content) {
        String message = jinFenOrderNotice.message();
        return StringUtils.isNotBlank(message)? message : MessageContants.DEFAULT_NULL;
    }
}
