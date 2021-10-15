package com.stupidzhang.weixin.service.strategy;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractionHandler {


    public abstract int order();

    public Boolean isHandle(String content) {
        content = StringUtils.lowerCase(content).trim();
        return compare(content);
    }

    protected abstract Boolean compare(String content);

    public abstract String handle(String openId, String content);
}
