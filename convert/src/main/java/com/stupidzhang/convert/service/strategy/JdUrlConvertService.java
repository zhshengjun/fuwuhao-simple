package com.stupidzhang.convert.service.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class JdUrlConvertService {

    public final static String DTK_APP_KEY = "609c83361bb32";
    public final static String DTK_APP_SECRET = "0f37aaf5b14a6127e68ac510eab75e92";
    public final static String TAO_BAO_TWD_TO_TWD = "https://openapi.dataoke.com/api/tb-service/twd-to-twd";
    public final static String VERSION = "version";
    public final static String CODE = "code";
    public final static String DATA = "data";

    public final static String JING_DONG_UNION = "https://openapi.dataoke.com/api/dels/jd/kit/promotion-union-convert";

    public final static String TAO_BAO_GOODS_ID = "https://openapi.dataoke.com/api/tb-service/parse-content";

    private String request(String url, TreeMap<String, Object> paramMap) {
        paramMap.put("appKey", DTK_APP_KEY);
        return ApiClient.sendReq(url, DTK_APP_SECRET, paramMap);
    }

    public String jdShortUrl(String originUrl) {
        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put(VERSION, "v1.0.0");
        paraMap.put("unionId", "1003138166");
        paraMap.put("materialId", originUrl);
        String result = request(JING_DONG_UNION, paraMap);


        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString(CODE);
        StringBuilder sb = new StringBuilder();
        if (Integer.parseInt(code) != 0) {
            sb.append("系统错误,获取链接失败，请更换商品链接");
        } else {
            JSONObject data = jsonObject.getJSONObject(DATA);
            String shortUrl = data.getString("shortUrl");
            if (StringUtils.isNotBlank(shortUrl)) {
                sb.append(shortUrl);
            }
        }
        return sb.toString();
    }
}
