package com.niezhiliang.simple.pay.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.niezhiliang.simple.pay.config.AlipayConfig;
import com.niezhiliang.simple.pay.dto.AlipayQrcodeDTO;
import com.niezhiliang.simple.pay.utils.JsonUtils;
import com.niezhiliang.simple.pay.vos.AlipayQrcodeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:24
 * 二维码生成策略
 */
@Slf4j
public class AlipayQrcodeStrategy implements PayStrategy<AlipayQrcodeVO,AlipayQrcodeDTO> {

    /**
     * 支付宝生成二维码
     * @param alipayQrcodeDTO
     * @return
     */
    @Override
    public AlipayQrcodeVO operate(AlipayQrcodeDTO alipayQrcodeDTO) {
        AlipayClient alipayClient = AlipayConfig.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayQrcodeDTO));
        request.setBizContent(JsonUtils.jsonFormat(alipayQrcodeDTO));
        request.setNotifyUrl(AlipayConfig.getInstance().getNotifyUrl());
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = null;
        try {
            alipayTradePrecreateResponse = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        JSONObject qrcodeResponse = JSON.parseObject(alipayTradePrecreateResponse.getBody());

        AlipayQrcodeVO qrcodeVO = qrcodeResponse.getObject("alipay_trade_precreate_response",AlipayQrcodeVO.class);

        return qrcodeVO;
    }
}
