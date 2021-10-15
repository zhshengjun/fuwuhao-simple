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
public class DisturbanceMessageHandler extends AbstractionHandler {


    @Autowired
    private WeiXinService weiXinService;

    @Override
    public int order() {
        return 1;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.startsWithAny(content, "td", "退订");
    }

    @Override
    public String handle(String openId, String content) {
        try {
            weiXinService.tagSet(new String[]{openId}, 107);
        } catch (WxErrorException e) {
            log.error("添加标签失败{},{}", openId, 107);
        }
        return "您已成功退订消息提醒，回复【订阅】可以恢复提醒";
    }
}
