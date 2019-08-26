package com.niezhiliang.simple.pay.strategy.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.niezhiliang.simple.pay.config.AlipayConfig;
import com.niezhiliang.simple.pay.dto.AliPayH5PayDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-08-26 11:15
 */
@Slf4j
public class AlipayH5PayStrategy implements PayStrategy<String, AliPayH5PayDTO> {
    @Override
    public String operate(AliPayH5PayDTO aliPayH5PayDTO) {

        AlipayClient alipayClient = AlipayConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizContent(JsonUtils.jsonFormat(aliPayH5PayDTO));

        AlipayTradeWapPayResponse tradeWapPayResponse = null;
        log.debug(JSON.toJSONString(request));
        try {
            tradeWapPayResponse = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(tradeWapPayResponse));
        return tradeWapPayResponse.getBody();
    }
}
