package com.stupidzhang.convert.service.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class DaTaoKeUrlConvertService {

    public final static String DTK_APP_KEY = "609c83361bb32";
    public final static String DTK_APP_SECRET = "0f37aaf5b14a6127e68ac510eab75e92";
    public final static String TAO_BAO_TWD_TO_TWD = "https://openapi.dataoke.com/api/tb-service/twd-to-twd";
    public final static String VERSION = "version";
    public final static String CODE = "code";
    public final static String DATA = "data";

    public final static String TAO_BAO_GOODS_ID = "https://openapi.dataoke.com/api/tb-service/parse-content";

    private String request(String url, TreeMap<String, Object> paramMap) {
        paramMap.put("appKey", DTK_APP_KEY);
        return ApiClient.sendReq(url, DTK_APP_SECRET, paramMap);
    }


    public String convertTaoBaoAll(String originUrl) {
        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put("version", "v1.0.0");
        paraMap.put("content", originUrl);
        return request(TAO_BAO_TWD_TO_TWD, paraMap);
    }

    public String taoBaoTwdToTwd(String originUrl) {
        String result = convertTaoBaoAll(originUrl);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null) {
            return "抱歉，系统出现异常，请稍后重试";
        }
        String code = jsonObject.getString("code");
        StringBuilder sb = new StringBuilder();
        if (Integer.parseInt(code) == 10074) {
            sb.append("淘口令无效或已过期,请输入正确的口令");
        } else if (Integer.parseInt(code) < 0) {
            sb.append("非常抱歉，您的商品未查询到相关优惠券");
        } else {
            JSONObject data = jsonObject.getJSONObject("data");
            String couponInfo = data.getString("couponInfo");
            String couponEndTime = data.getString("couponEndTime");

            sb.append(data.getString("longTpwd"));
            if (StringUtils.isBlank(couponInfo)) {
                sb.append("  非常抱歉，您的商品未查询到相关优惠券");
            } else {
                sb.append("  ").append("fu至内容app即可领取【").append(couponInfo).append("】优惠券 限").append(couponEndTime).append("前使用");
            }
        }
        return sb.toString();
    }

    /**
     * 获取链接对应的商品id
     *
     * @param originUrl
     * @return
     */
    public String analyzeTaoBaoGoodsId(String originUrl) {
        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put(VERSION, "v1.0.0");
        paraMap.put("content", originUrl);
        String response = request(TAO_BAO_GOODS_ID, paraMap);
        JSONObject result = JSON.parseObject(response);
        Integer code = result.getInteger(CODE);
        if (code == 0) {
            return result.getJSONObject(DATA).getString("goodsId");
        }
        return null;
    }

    /**
     * 获取长口令
     *
     * @param originUrl
     * @return
     */
    public String queryTpwd(String originUrl) {
        String result = convertTaoBaoAll(originUrl);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject data = jsonObject.getJSONObject(DATA);
        return data.getString("longTpwd");
    }

    /**
     * 获取产品信息
     *
     * @param originUrl
     * @return
     */
    public String itemInfo(String originUrl) {
        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put(VERSION, "v1.0.0");
        paraMap.put("content", originUrl);
        String response = request(TAO_BAO_GOODS_ID, paraMap);
        JSONObject result = JSON.parseObject(response);
        Integer code = result.getInteger(CODE);
        if (code == 0) {
            return result.getJSONObject(DATA).toString();
        }
        return null;
    }
}
