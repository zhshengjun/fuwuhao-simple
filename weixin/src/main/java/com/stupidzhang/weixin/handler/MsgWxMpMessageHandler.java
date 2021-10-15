package com.stupidzhang.weixin.handler;


import com.stupidzhang.weixin.builder.TextBuilder;
import com.stupidzhang.weixin.contants.MessageContants;
import com.stupidzhang.weixin.service.WxMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@Component
public class MsgWxMpMessageHandler extends AbstractWxMpMessageHandler {

    @Autowired
    private WxMessageService wxMessageService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            log.warn("收到{}的消息:{}", wxMessage.getFromUser(), wxMessage.getContent());
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        String content = "";
        try {
            content = wxMessageService.handle(wxMessage.getFromUser(), wxMessage.getContent());
            log.warn("处理后的消息内容：{}", content);
            if (MessageContants.ORDER_SEND_MESSAGE.equals(content) || MessageContants.DEFAULT_NULL.equals(content)) {
                return null;
            }
        } catch (WxRuntimeException e) {
            content = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (MessageContants.WAIMAI_CPS.equals(content) || MessageContants.XIAO_KE_FU.equals(content)) {
            return null;
        } else if (StringUtils.isBlank(content) || MessageContants.NO_HANDLE_MESSAGE.equals(content)) {
            content = "您输入的【" + wxMessage.getContent() + "】未能正确识别，请输入正确的指令哦~~";
        }
        return new TextBuilder().build(content, wxMessage, wxMpService);

    }
}
