package com.stupidzhang.weixin.service;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WeiXinService {


    private WxMpService wxMpService;

    @Autowired
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }


    public WxMpUserList userList(String nextOpenid) throws WxErrorException {
        return wxMpService.getUserService().userList(nextOpenid);
    }

    /**
     * 获取标签
     *
     * @return
     * @throws WxErrorException
     */
    public List<WxUserTag> tagGet() throws WxErrorException {

        return wxMpService.getUserTagService().tagGet();
    }

    public List<Long> userTag(String openId) throws WxErrorException {
        return wxMpService.getUserTagService().userTagList(openId);
    }

    public void tagSet(String[] openIds, long tagId) throws WxErrorException {
        wxMpService.getUserTagService().batchTagging(tagId, openIds);
    }

    public void tagRemove(String[] openIds, long tagId) throws WxErrorException {
        wxMpService.getUserTagService().batchUntagging(tagId, openIds);
    }

    public WxMpUser userInfo(String openid) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openid, "zh_CN");
    }

    public void templateMessage(String openId, String templateId, String url, List<WxMpTemplateData> templateDataList, WxMpTemplateMessage.MiniProgram miniProgram) throws WxErrorException {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId).templateId(templateId).data(templateDataList).build();

        if (StringUtils.isNotBlank(url)) {
            templateMessage.setUrl(url);
        }
        if (miniProgram != null) {
            templateMessage.setMiniProgram(miniProgram);
        }
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }

    public String getAccessToken() throws WxErrorException {

        return wxMpService.getAccessToken();
    }
}
