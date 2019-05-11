package com.niezhiliang.simple.pay.strategy.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.niezhiliang.simple.pay.config.AlipayConfig;
import com.niezhiliang.simple.pay.dto.AlipayPcPayDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.JsonUtils;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 下午3:53
 * PC端下单策略
 */
public class AliPayPcPayStrategy implements PayStrategy<String,AlipayPcPayDTO> {

    /**
     * PC端电脑支付固定值
     */
    private final static String FAST_INSTANT_TRADE_PAY = "FAST_INSTANT_TRADE_PAY";

    @Override
    public String operate(AlipayPcPayDTO pcPreOrderDTO) {
        AlipayClient alipayClient =  AlipayConfig.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置回调页面
        alipayRequest.setReturnUrl(AlipayConfig.getInstance().getReturnUrl());
        //回调参数
        alipayRequest.setNotifyUrl(AlipayConfig.getInstance().getNotifyUrl());
        //官网支付固定值
        pcPreOrderDTO.setProductCode(FAST_INSTANT_TRADE_PAY);
        alipayRequest.setBizContent(JsonUtils.jsonFormat(pcPreOrderDTO));
        String form="";
        try {
            /**
             * 调用SDK生成表单
             */
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }
}
