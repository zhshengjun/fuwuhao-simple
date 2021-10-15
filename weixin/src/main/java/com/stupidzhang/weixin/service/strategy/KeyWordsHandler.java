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
        return 999;
    }

    @Override
    public Boolean compare(String content) {
        return true;
    }

    @Override
    public String handle(String openId, String content) {
        String result = keywordsService.getKeysReply(content);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }
        return MessageContants.DEFAULT_NULL;
    }
}
