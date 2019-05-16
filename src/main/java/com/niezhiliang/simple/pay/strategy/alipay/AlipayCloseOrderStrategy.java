package com.niezhiliang.simple.pay.strategy.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.niezhiliang.simple.pay.config.AlipayConfig;
import com.niezhiliang.simple.pay.dto.AlipayCloseOrderDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.JsonUtils;
import com.niezhiliang.simple.pay.vos.AlipayCloseOrderVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/15 下午2:19
 * 支付宝订单关闭策略
 */
@Slf4j
public class AlipayCloseOrderStrategy implements PayStrategy<AlipayCloseOrderVO,AlipayCloseOrderDTO> {

    @Override
    public AlipayCloseOrderVO operate(AlipayCloseOrderDTO alipayCloseOrderDTO) {

        AlipayClient alipayClient = AlipayConfig.getAlipayClient();
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayCloseOrderDTO));
        AlipayTradeCloseResponse tradeCloseResponse = null;
        log.debug(JSON.toJSONString(request));
        try {
            tradeCloseResponse = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        log.debug(JSON.toJSONString(tradeCloseResponse));

        JSONObject qrcodeResponse = JSON.parseObject(tradeCloseResponse.getBody());

        AlipayCloseOrderVO alipayCloseOrderVO = qrcodeResponse.getObject("alipay_trade_close_response",AlipayCloseOrderVO.class);
        return alipayCloseOrderVO;
    }
}
