package com.niezhiliang.simple.pay.strategy.alipay;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.niezhiliang.simple.pay.config.AlipayConfig;
import com.niezhiliang.simple.pay.dto.AlipayRefundQueryDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.JsonUtils;
import com.niezhiliang.simple.pay.vos.AlipayRefundQueryVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/11 下午2:26
 * 支付宝退款查询策略
 */
@Slf4j
public class AlipayRefundQueryStrategy implements PayStrategy<AlipayRefundQueryVO,AlipayRefundQueryDTO> {

    @Override
    public AlipayRefundQueryVO operate(AlipayRefundQueryDTO alipayRefundQueryDTO) {
        AlipayClient alipayClient = AlipayConfig.getAlipayClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        //如果退款的时候商户退款单号并没有填写，则默认和商户交易流水号一样
        if(alipayRefundQueryDTO.getOutRequestNo() == null || "".equals(alipayRefundQueryDTO.getOutRequestNo())) {
            alipayRefundQueryDTO.setOutRequestNo(alipayRefundQueryDTO.getOutTradeNo());
        }
        request.setBizContent(JsonUtils.jsonFormat(alipayRefundQueryDTO));
        AlipayTradeFastpayRefundQueryResponse response = null;
        log.debug(JSON.toJSONString(request));
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(response));
        JSONObject refundResponse = JSON.parseObject(response.getBody());

        return refundResponse.getObject("alipay_trade_fastpay_refund_query_response",AlipayRefundQueryVO.class);
    }
}
