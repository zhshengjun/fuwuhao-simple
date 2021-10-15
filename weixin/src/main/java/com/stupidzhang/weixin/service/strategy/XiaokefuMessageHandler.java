package com.stupidzhang.weixin.service.strategy;

import com.stupidzhang.weixin.contants.MessageContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class XiaokefuMessageHandler extends AbstractionHandler {


    @Override
    public int order() {
        return 6;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.startsWithAny(content, "#a");
    }

    @Override
    public String handle(String openId, String content) {
        return MessageContants.XIAO_KE_FU;
    }
}
