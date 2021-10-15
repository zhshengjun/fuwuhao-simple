package com.stupidzhang.weixin.service.strategy;


import com.stupidzhang.weixin.contants.MessageContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class WaimaiMessageHandler extends AbstractionHandler {


    @Override
    public int order() {
        return 0;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.containsAny(content, "饿了么", "饿了么红包", "美团外卖", "美团", "外卖");
    }

    @Override
    public String handle(String openId, String content) {
        return MessageContants.WAIMAI_CPS;
    }
}
