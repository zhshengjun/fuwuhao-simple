package com.stupidzhang.weixin.service;

import com.stupidzhang.jingfen.constant.JdConstants;
import com.stupidzhang.jingfen.mode.WeiXinMessageEntity;
import com.stupidzhang.jingfen.service.JingFenApiService;
import com.stupidzhang.weixin.contants.WxMessageConstants;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RefreshScope
@Slf4j
@Service
public class JinFenOrderNotice {

    @Value("${jingfen.local:true}")
    private boolean local;

    @Value("${jingfen.toUser}")
    private String openId;

    @Value("${jingfen.orderTemplateId}")
    private String orderTemplateId;

    @Autowired
    private JingFenApiService jingFenApiService;

    @Autowired
    private WxMpService wxMpService;

    public String message() {
        log.warn("local:{} | openId:{} | orderTemplateId:{}", local, openId, orderTemplateId);
        if (!local) {
            return "当前环境不发送消息！";
        }
        LocalDateTime orderTime = LocalDateTime.now().withNano(0);
        LocalDateTime startTime = orderTime.minusMinutes(60);
        String endTimeStr = orderTime.format(DateTimeFormatter.ofPattern(JdConstants.DATE_TIME_FORMAT));
        String startTimeStr = startTime.format(DateTimeFormatter.ofPattern(JdConstants.DATE_TIME_FORMAT));
        WeiXinMessageEntity weiXinMessageEntity = jingFenApiService.queryOrderList(startTimeStr, endTimeStr);
        if (weiXinMessageEntity == null) {
            log.warn("未获取到订单信息！");
            return "未获取到订单信息！";
        }
        List<WxMpTemplateData> templateDataList = new ArrayList<>();
        templateDataList.add(new WxMpTemplateData(WxMessageConstants.MESSAGE_FIRST, weiXinMessageEntity.getFirst().getValue(), weiXinMessageEntity.getFirst().getColor()));
        templateDataList.add(new WxMpTemplateData(WxMessageConstants.MESSAGE_KEYWORD_ONE, weiXinMessageEntity.getKeyword1().getValue(), weiXinMessageEntity.getKeyword1().getColor()));
        templateDataList.add(new WxMpTemplateData(WxMessageConstants.MESSAGE_KEYWORD_TWO, weiXinMessageEntity.getKeyword2().getValue(), weiXinMessageEntity.getKeyword2().getColor()));
        templateDataList.add(new WxMpTemplateData(WxMessageConstants.MESSAGE_KEYWORD_THREE, weiXinMessageEntity.getKeyword3().getValue(), weiXinMessageEntity.getKeyword3().getColor()));
        // 发送消息
        sendMessage(openId, orderTemplateId, templateDataList);
        return "";
    }

    public void sendMessage(String openId, String templateId, List<WxMpTemplateData> templateDataList) {
        try {
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(openId).templateId(templateId).data(templateDataList).build();
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception exception) {
            log.error("发送微信消息失败，exception：{}", exception.getMessage());
        }
    }
}
