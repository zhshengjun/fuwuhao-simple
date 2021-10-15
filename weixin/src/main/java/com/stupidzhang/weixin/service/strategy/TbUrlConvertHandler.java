package com.stupidzhang.weixin.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RefreshScope
public class TbUrlConvertHandler extends AbstractUrlConvertHandler {


    @Override
    public int order() {
        return 4;
    }

    @Override
    public Boolean compare(String content) {
        return StringUtils.containsAny(content, "https://m.tb.cn", "http://m.tb.cn", "嘻", "哈", "信", "啊", "hi:/", "ha:/")
                && (content.length() > 10 && StringUtils.isNumeric(content.substring(0, 1)));
    }

    @Override
    protected String dataSource() {
        return "tb";
    }
}
