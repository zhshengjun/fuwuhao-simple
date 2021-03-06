package com.stupidzhang.weixin.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

public class ImageWxMpMessageHandler extends AbstractWxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) {
        try {
            WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService()
                    .mediaUpload(WxConsts.MediaFileType.IMAGE, "jpeg", ClassLoader.getSystemResourceAsStream("mm.jpeg"));
            WxMpXmlOutImageMessage m
                    = WxMpXmlOutMessage
                    .IMAGE()
                    .mediaId(wxMediaUploadResult.getMediaId())
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            return m;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    }
}
