package com.stupidzhang.weixin.service.strategy;

import com.stupidzhang.keywords.service.KeywordsService;
import com.stupidzhang.weixin.contants.MessageContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyWordsHandler extends AbstractionHandler {

    @Autowired
    private KeywordsService keywordsService;

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Boolean compare(String content) {
        return keywordsService.match(content);
    }

    @Override
    public String handle(String openId, String content) {
        return keywordsService.getKeysReply(content);
    }
}
