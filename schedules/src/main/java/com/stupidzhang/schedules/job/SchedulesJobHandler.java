package com.stupidzhang.schedules.job;


import com.stupidzhang.jingfen.service.UrlCommitService;
import com.stupidzhang.weixin.service.JinFenOrderNotice;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SchedulesJobHandler {

    @Autowired
    private JinFenOrderNotice jinFenOrderNotice;

    @Autowired
    private UrlCommitService urlCommitService;

    @XxlJob("schedulesJobHandler")
    public ReturnT<String> schedulesJobHandler() {
        jinFenOrderNotice.message();
        log.warn("京粉订单消息定时任务调度成功！");
        return ReturnT.SUCCESS;
    }

    @XxlJob("urlCommitJobHandler")
    public ReturnT<String> urlCommitJobHandler() {
        urlCommitService.commit();
        log.warn("url消息成功");
        return ReturnT.SUCCESS;
    }


    @XxlJob("testJobHandler")
    public ReturnT<String> testJobHandler() {
        log.warn("测试任务接收成功");
        return ReturnT.SUCCESS;
    }

}
