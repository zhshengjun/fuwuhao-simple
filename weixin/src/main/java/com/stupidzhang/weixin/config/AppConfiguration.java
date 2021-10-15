package com.stupidzhang.weixin.config;


import com.binarywang.spring.starter.wxjava.mp.properties.WxMpProperties;
import com.stupidzhang.weixin.handler.MsgWxMpMessageHandler;
import com.stupidzhang.weixin.handler.NullWxMpMessageHandler;
import com.stupidzhang.weixin.handler.SubscribeWxMpMessageHandler;
import com.stupidzhang.weixin.handler.UnsubscribeWxMpMessageHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({WxMpProperties.class})
public class AppConfiguration {


    private final NullWxMpMessageHandler nullMessageHandler;
    private final MsgWxMpMessageHandler msgMessageHandler;
    private final SubscribeWxMpMessageHandler subscribeMessageHandler;
    private final UnsubscribeWxMpMessageHandler unsubscribeMessageHandler;

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.VIEW).handler(this.nullMessageHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeMessageHandler).end();
        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeMessageHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgMessageHandler).end();

        return newRouter;
    }

}
