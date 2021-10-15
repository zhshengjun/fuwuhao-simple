package com.stupidzhang.weixin.service.strategy;


import com.stupidzhang.weixin.contants.MessageContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 京东转链
 */
@Service
public class JdUrlConvertHandler extends AbstractUrlConvertHandler {

    @Override
    public int order() {
        return 3;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.startsWithAny(content, MessageContants.CONTENT_URL_PR, MessageContants.CONTENT_URL_PR_2, MessageContants.CONTENT_URL_PR_3);
    }

    @Override
    protected String dataSource() {
        return "jd";
    }
}
