package com.stupidzhang.convert.service.strategy;


import cn.hutool.http.HttpUtil;
import com.stupidzhang.convert.utils.SignMD5Util;

import java.util.TreeMap;

import static cn.hutool.http.HttpUtil.get;

/**
 * @projectName:dtk-items-openapi
 * @createTime: 2019年11月28日14:13:21
 * @description:
 */
public class ApiClient {

    public static String sendReqNew(String url, String secret, TreeMap<String, Object> paraMap) {
        if (null == url || "".equals(url)) {
            return "请求地址不能为空";
        }
        if (null == secret || "".equals(secret)) {
            return "secret不能为空";
        }
        if (null == paraMap || paraMap.size() < 1) {
            return "参数不能为空";
        }

        String timer = String.valueOf(System.currentTimeMillis());
        paraMap.put("timer", timer);
        paraMap.put("nonce", "110505");
        paraMap.put("signRan", SignMD5Util.getSignStrNew(paraMap, secret));
        String data = "";
        try {
            data = get(url, paraMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String sendReq(String url, String secret, TreeMap<String, Object> paraMap) {
        if (null == url || "".equals(url)) {
            return "请求地址不能为空";
        }
        if (null == secret || "".equals(secret)) {
            return "secret不能为空";
        }
        if (null == paraMap || paraMap.size() < 1) {
            return "参数不能为空";
        }

        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, secret));
        String data = "";
        try {
            data = HttpUtil.get(url, paraMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


}

