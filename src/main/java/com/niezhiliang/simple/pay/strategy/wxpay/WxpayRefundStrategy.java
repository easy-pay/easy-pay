package com.niezhiliang.simple.pay.strategy.wxpay;


import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.dto.WxpayRefundDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxpayRefundVO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:38
 * 微信退款策略
 */
@Slf4j
public class WxpayRefundStrategy implements PayStrategy<WxpayRefundVO,WxpayRefundDTO> {

    @Override
    public WxpayRefundVO operate(WxpayRefundDTO wxRefundDTO) {
        log.info("微信退款商户订单号：{},微信退款商户退款单号：{}", wxRefundDTO.getOutTradeNo(),wxRefundDTO.getOutRefundNo());

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxRefundDTO.setAppId(wxPayConfig.getAppId());
        wxRefundDTO.setMchId(wxPayConfig.getMchId());
        wxRefundDTO.setNonceStr(wxRefundDTO.getOutTradeNo());
        wxRefundDTO.setRefundNotifyUrl(wxPayConfig.getRefundNotify());
        //微信支付提交的金额是不能带小数点的，且是以分为单位，所以我们系统如果是以元为单位要处理下金额，即先乘以100，再去小数点
        wxRefundDTO.setRefundFee(new BigDecimal(wxRefundDTO.getRefundFee()).multiply(new BigDecimal("100")).intValue()+"");
        wxRefundDTO.setTotalFee(new BigDecimal(wxRefundDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()+"");
        wxRefundDTO.setSign(SignUtils.createSign(wxRefundDTO, "MD5", wxPayConfig.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxRefundDTO);
        log.info(xml);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doRefund(APIURLENUMS.API_URL_REFUND.getUrl(),xml);
            log.info("微信退款申请返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WxpayRefundVO wxpayRefundVO =  WxpayRefundVO.fromXML(responseContent,WxpayRefundVO.class);
        //因为微信返回的是分，这里我们将分转换成元
        if (wxpayRefundVO.getResultCode().equals("SUCCESS")) {
            wxpayRefundVO.setRefundFee(new BigDecimal(wxpayRefundVO.getRefundFee()).divide(new BigDecimal("100")).floatValue()+"");
            wxpayRefundVO.setTotalFee(new BigDecimal(wxpayRefundVO.getTotalFee()).divide(new BigDecimal("100")).floatValue()+"");
        }
        return wxpayRefundVO;
    }
}
