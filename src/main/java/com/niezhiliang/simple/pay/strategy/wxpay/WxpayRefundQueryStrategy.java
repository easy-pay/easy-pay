package com.niezhiliang.simple.pay.strategy.wxpay;

import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WxpayRefundQueryDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxpayRefundQueryVO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午3:45
 * 退款查询
 */
@Slf4j
public class WxpayRefundQueryStrategy implements PayStrategy<WxpayRefundQueryVO,WxpayRefundQueryDTO> {

    @Override
    public WxpayRefundQueryVO operate(WxpayRefundQueryDTO wxRefundQueryDTO) {
        log.debug("微信退款查询订单号：{}", wxRefundQueryDTO.getOutTradeNo());

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxRefundQueryDTO.setAppId(wxPayConfig.getAppId());
        wxRefundQueryDTO.setMchId(wxPayConfig.getMchId());
        wxRefundQueryDTO.setNonceStr(wxRefundQueryDTO.getOutTradeNo());
        wxRefundQueryDTO.setSign(SignUtils.createSign(wxRefundQueryDTO, "MD5", wxPayConfig.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxRefundQueryDTO);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_REFUND_QUERY.getUrl(),xml);
            log.debug("微信退款查询返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WxpayRefundQueryVO wxpayRefundQueryVO = WxpayRefundQueryVO.fromXML(responseContent,WxpayRefundQueryVO.class);
        if (wxpayRefundQueryVO.getResultCode().equals("SUCCESS")) {
            wxpayRefundQueryVO.setRefundFee(new BigDecimal(wxpayRefundQueryVO.getRefundFee()).divide(new BigDecimal("100")).floatValue()+"");
        }

        return wxpayRefundQueryVO;
    }
}
