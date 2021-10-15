package com.stupidzhang.convert.service.strategy;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 *
 */
@Service
@Slf4j
public class DingDanXiaUrlConvertService {

    public final static String DDX_APP_KEY = "TvfQTIxYFjlbjHdmUI4UG9JM8pQRAsfu";
    public final static String TAO_BAO_DDX_WN_CONVERT = "http://api.tbk.dingdanxia.com/tbk/wn_convert";


    public String queryInfo(String originPtwd) {
        // 调用订单侠的
        HashMap<String, Object> params = new HashMap<>(16);
        params.put("apikey", DDX_APP_KEY);
        params.put("content", originPtwd);
        params.put("pid", "mm_1304830017_1919900083_111361100472");
        params.put("tpwd", true);
        params.put("text", true);
        params.put("itemInfo", true);
        params.put("extspk", true);
        params.put("shorturl", true);

        return HttpUtil.post(TAO_BAO_DDX_WN_CONVERT, params);
    }

    public String convert(String originUrl) {
        String result = queryInfo(originUrl);
        log.warn("接口返回信息：{}", result);
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        StringBuilder sb = new StringBuilder();
        if (Integer.parseInt(code) < 0) {
            sb.append("非常抱歉，您的商品未查询到相关优惠券");
        } else if (Integer.parseInt(code) == 4012 || Integer.parseInt(code) == 4013) {
            log.warn("当前IP被拒绝：{}", jsonObject.getString("msg"));
        } else {
            JSONObject data = jsonObject.getJSONObject("data");
            String coupon = data.getString("coupon");
            String couponEndTime = data.getString("coupon_end_time");

            sb.append(data.getString("long_coupon_tpwd"));
            if (StringUtils.isBlank(coupon) || "0".equals(coupon)) {
                sb.append("  非常抱歉，您的商品未查询到相关优惠券");
            } else {
                sb.append("\n").append("fu至内容App即可领取 ").append(coupon).append(" 元优惠券 限").append(couponEndTime).append("前使用");
            }
        }
        return sb.toString();
    }

    /**
     * 返回淘口令
     *
     * @param originUrl 原始链接
     * @return omit
     */
    public String convertCouponPwd(String originUrl) {
        String result = queryInfo(originUrl);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        return data.getString("coupon_tpwd");
    }


}
