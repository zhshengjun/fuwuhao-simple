package com.stupidzhang.weixin.service;


import com.stupidzhang.weixin.contants.MessageContants;
import com.stupidzhang.weixin.service.strategy.AbstractionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class WxMessageService {

    @Resource
    List<AbstractionHandler> handlers;

    public String handle(String openId, String content) {
        if (content == null) {
            content = "";
        }
        handlers.sort(Comparator.comparing(AbstractionHandler::order));
        for (AbstractionHandler handler : handlers) {
            if (Boolean.TRUE.equals(handler.isHandle(content))) {
                log.info("Handler处理中 openId: {},content :{}", openId, content);
                return handler.handle(openId, content);
            }
        }
        return MessageContants.DEFAULT_NULL;
    }
}
