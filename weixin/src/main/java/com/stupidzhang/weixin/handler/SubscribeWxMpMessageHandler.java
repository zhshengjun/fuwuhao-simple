package com.stupidzhang.weixin.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@Component
public class SubscribeWxMpMessageHandler extends AbstractWxMpMessageHandler {


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        /*try {
            WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                WxAttention wxAttention = wxAttentionService.queryByOpenId(wxMessage.getFromUser());
                if (wxAttention == null) {
                    wxAttention = WxAttention.builder().build();
                    wxAttention.setOpenId(wxMessage.getFromUser());
                }
                wxAttention.setActive(true);
                wxAttention.setNotice(false);
                if (wxAttention.getId() == null) {
                    wxAttentionService.insert(wxAttention);
                } else {
                    wxAttentionService.updateById(wxAttention);
                }
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("感谢关注\n");
        sb.append("回复appKey: xxxxxxxxxx，即可设置京东key\n");
        sb.append("回复appSecret: xxxxxxxxxx，即可设置京东appSecret\n");
        sb.append("回复active，激活消息通知\n");
        sb.append("回复 1～60，即可查看近一小时的订单情况\n");
        sb.append("回复delete，即可删除京东配置\n");


        try {
            return new TextBuilder().build(sb.toString(), wxMessage, wxMpService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }*/

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}
