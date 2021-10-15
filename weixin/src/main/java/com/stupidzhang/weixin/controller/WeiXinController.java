package com.stupidzhang.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.stupidzhang.weixin.service.WeiXinService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weixin")
public class WeiXinController {
    @Autowired
    private WeiXinService weiXinService;


    @GetMapping("/tag/list")
    public String tagList() {
        List<WxUserTag> wxUserTags;
        try {
            wxUserTags = weiXinService.tagGet();
        } catch (WxErrorException e) {
            return e.getMessage();
        }
        return JSON.toJSONString(wxUserTags);
    }

    @GetMapping("/token")
    public String getAccessToken() {
        String wxUserTags;
        try {
            wxUserTags = weiXinService.getAccessToken();
        } catch (WxErrorException e) {
            return e.getMessage();
        }
        return JSON.toJSONString(wxUserTags);
    }

}
