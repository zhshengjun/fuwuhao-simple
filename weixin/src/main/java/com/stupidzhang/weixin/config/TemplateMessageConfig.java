package com.stupidzhang.weixin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateMessageConfig {

    @Value("${fwh.template.remind.filter.tag}")
    public long filterTagCode;

    @Value("${fwh.template.message.code}")
    public String templateCode ;

    @Value("${fwh.template.default.openid}")
    public String defaultOpenId;

}
