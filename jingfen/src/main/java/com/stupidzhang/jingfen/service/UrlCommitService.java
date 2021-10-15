package com.stupidzhang.jingfen.service;

import cn.hutool.http.HttpUtil;
import com.stupidzhang.jingfen.mapper.UrlCommitMapper;
import com.stupidzhang.jingfen.mode.UrlCommit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UrlCommitService {

    @Autowired
    private UrlCommitMapper urlCommitMapper;

    private static final String GOOGLE_URL = "https://www.google.com/ping";

    public void commit() {
        List<UrlCommit> urlCommits = urlCommitMapper.selectAll();
        Map<String, Object> params = new HashMap<>(2);
        for (UrlCommit urlCommit : urlCommits) {
            params.put("sitemap", urlCommit.getUrl());
            try {
                HttpUtil.get(GOOGLE_URL, params);
                log.warn("提交链接：{}", urlCommit.getUrl());
                Thread.sleep(1000);
            } catch (Exception exception) {
                log.error("提交链接失败：{}", urlCommit.getUrl());
            }


        }
    }
}
