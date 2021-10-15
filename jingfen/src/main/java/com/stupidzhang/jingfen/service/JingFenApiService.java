package com.stupidzhang.jingfen.service;


import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.kplunion.OrderService.request.query.OrderRowReq;
import com.jd.open.api.sdk.domain.kplunion.OrderService.response.query.OrderRowQueryResult;
import com.jd.open.api.sdk.domain.kplunion.OrderService.response.query.OrderRowResp;
import com.jd.open.api.sdk.request.kplunion.UnionOpenOrderRowQueryRequest;
import com.jd.open.api.sdk.response.kplunion.UnionOpenOrderRowQueryResponse;
import com.stupidzhang.jingfen.enums.OrderStatusEnum;
import com.stupidzhang.jingfen.mode.EntityBase;
import com.stupidzhang.jingfen.mode.WeiXinMessageEntity;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 京东订单查询接口
 */
@Slf4j
@Service
public class JingFenApiService {

    public final static String JD_API_URL = "https://api.jd.com/routerjson";

    @Value("${jingfen.appKey}")
    private String appKey;

    @Value("${jingfen.secretKey}")
    private String secretKey;


    /**
     * 查询订单列表方法
     *
     * @param startTime 查询开始时间
     * @param endTime   查询结束时间
     */
    public WeiXinMessageEntity queryOrderList(String startTime, String endTime) throws WxRuntimeException {
        UnionOpenOrderRowQueryRequest request = buildRequest(startTime, endTime);
        UnionOpenOrderRowQueryResponse response;
        try {
            JdClient client = new DefaultJdClient(JD_API_URL, "", appKey, secretKey,
                    30000, 90000);
            response = client.execute(request);
        } catch (Exception exception) {
            log.error("【{}~{}】期间请求数据出错了！！！", startTime, endTime);
            return null;
        }
        if (!"0".equals(response.getCode())) {
            log.error("【{}~{}】期间请求数据出错，原因：{}", startTime, endTime, response.getZhDesc());
            throw new WxRuntimeException(response.getZhDesc());
        }
        OrderRowQueryResult queryResult = response.getQueryResult();
        if (queryResult.getData() != null) {
            List<OrderRowResp> originList = Arrays.asList(queryResult.getData());
            // 过滤无效的子订单
            List<OrderRowResp> orderList = originList.stream()
                    .filter(order -> !(order.getParentId() == 0
                            && (OrderStatusEnum.TWO.getCode().equals(order.getValidCode())
                            || OrderStatusEnum.THREE.getCode().equals(order.getValidCode()))
                    ))
                    .collect(Collectors.toList());
            if (orderList.isEmpty()) {
                log.warn("非常遗憾，新增订单为无效订单，详情见京粉APP");
                return null;
            }
            log.warn("【{}~{}】期间新增订单啦", startTime, endTime);
            return buildOrder(orderList);
        }

        return null;
    }

    /**
     * 构建请求实体
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private UnionOpenOrderRowQueryRequest buildRequest(String startTime, String endTime) {
        UnionOpenOrderRowQueryRequest request = new UnionOpenOrderRowQueryRequest();
        OrderRowReq orderReq = new OrderRowReq();
        orderReq.setStartTime(startTime);
        orderReq.setEndTime(endTime);
        orderReq.setPageIndex(1);
        orderReq.setPageSize(50);
        orderReq.setType(1);
        request.setOrderReq(orderReq);
        request.setVersion("1.0");
        return request;
    }

    /**
     * 构建实体，用于发送订单消息
     *
     * @param orderList 订单列表
     * @return omit
     */
    private WeiXinMessageEntity buildOrder(List<OrderRowResp> orderList) {

        // 订单数量
        long orderNum = orderList.stream().map(OrderRowResp::getOrderId).distinct().count();
        double estimateFee = orderList.stream()
                .filter(orderRowResp -> OrderStatusEnum.SIXTEEN.getCode().equals(orderRowResp.getValidCode()))
                .mapToDouble(OrderRowResp::getEstimateFee).sum();
        double eEstimateCosPrice = orderList.stream().mapToDouble(OrderRowResp::getEstimateCosPrice).sum();
        WeiXinMessageEntity entity = new WeiXinMessageEntity();
        entity.setFirst(new EntityBase(String.valueOf(orderNum), "#5d6375"));
        entity.setKeyword1(new EntityBase(String.format("%.2f", estimateFee), "#9e0606"));
        entity.setKeyword2(new EntityBase(String.format("%.2f", eEstimateCosPrice), "#5d6375"));
        entity.setKeyword3(new EntityBase(orderItems(orderList), "#5d6375"));
        return entity;
    }

    /**
     * 拼装订单详情
     *
     * @param orderList 订单列表
     * @return 订单详情
     */
    private String orderItems(List<OrderRowResp> orderList) {
        StringBuilder contentBuilder = new StringBuilder();
        AtomicInteger orderSeq = new AtomicInteger(1);
        orderList.forEach(orderRowResp -> {
            String skuName = orderRowResp.getSkuName();
            Integer plus = orderRowResp.getPlus();
            Integer traceType = orderRowResp.getTraceType();
            OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatus(orderRowResp.getValidCode());
            String traceTypeName = traceType == 2 ? "同店" : "跨店";
            // 订单类型
            contentBuilder.append(orderSeq.getAndIncrement()).append("、");
            contentBuilder.append("【").append(traceTypeName).append("-").append(orderStatus.getState());
            // 无效原因
            if (StringUtils.isNotBlank(orderStatus.getReason())) {
                contentBuilder.append("-").append(orderStatus.getReason());
            }
            if (plus == 1) {
                contentBuilder.append("-").append("PLUS");
            }
            contentBuilder.append("】");
            contentBuilder.append(StringUtils.substring(skuName, 0, Math.min(skuName.length(), 30)))
                    .append("...").append("\n");
        });
        return contentBuilder.toString();
    }


}
