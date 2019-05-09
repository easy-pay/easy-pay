package com.niezhiliang.simple.pay.strategy;


import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.dto.WXRefundDTO;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxPayCloseOrderVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:38
 * 微信退款策略
 */
@Slf4j
public class WXRefundStrategy implements PayStrategy<WxPayCloseOrderVO,WXRefundDTO> {

    @Override
    public WxPayCloseOrderVO operate(WXRefundDTO wxRefundDTO) {
        log.info("微信退款商户订单号：{},微信退款商户退款单号", wxRefundDTO.getOutTradeNo(),wxRefundDTO.getOutRefundNo());

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxRefundDTO.setAppid(wxPayConfig.getAppId());
        wxRefundDTO.setMch_id(wxPayConfig.getMchId());
        wxRefundDTO.setNonce_str(wxRefundDTO.getOutTradeNo());
        wxRefundDTO.setRefundNotifyUrl(wxPayConfig.getRefundNotify());
        //微信支付提交的金额是不能带小数点的，且是以分为单位，所以我们系统如果是以元为单位要处理下金额，即先乘以100，再去小数点
        wxRefundDTO.setRefundFee(Integer.parseInt(wxRefundDTO.getRefundFee()) * 100+"");
        wxRefundDTO.setTotalFee(Integer.parseInt(wxRefundDTO.getTotalFee()) * 100+"");
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
        return WxPayCloseOrderVO.fromXML(responseContent,WxPayCloseOrderVO.class);
    }
}
