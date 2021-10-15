package com.stupidzhang.weixin.service.strategy;

import com.stupidzhang.weixin.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息退订免打扰
 */
@Slf4j
@Service
public class UnDisturbanceMessageHandler extends AbstractionHandler {


    @Autowired
    private WeiXinService weiXinService;

    @Override
    public int order() {
        return 2;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.startsWithAny(content, "订阅");
    }

    @Override
    public String handle(String openId, String content) {

        try {
            weiXinService.tagRemove(new String[]{openId}, 107);
        } catch (WxErrorException e) {
            log.error("移除标签失败{},{}", openId, 107);
        }
        return "订阅成功";
    }
}
