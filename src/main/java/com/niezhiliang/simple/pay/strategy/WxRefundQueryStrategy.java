package com.niezhiliang.simple.pay.strategy;

import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WxRefundQueryDTO;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxRefundQueryVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午3:45
 * 退款查询
 */
@Slf4j
public class WxRefundQueryStrategy implements PayStrategy<WxRefundQueryVO,WxRefundQueryDTO> {

    @Override
    public WxRefundQueryVO operate(WxRefundQueryDTO wxRefundQueryDTO) {
        log.info("微信退款查询订单号：{}", wxRefundQueryDTO.getOutTradeNo());

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxRefundQueryDTO.setAppid(wxPayConfig.getAppId());
        wxRefundQueryDTO.setMch_id(wxPayConfig.getMchId());
        wxRefundQueryDTO.setNonce_str(wxRefundQueryDTO.getOutTradeNo());
        wxRefundQueryDTO.setSign(SignUtils.createSign(wxRefundQueryDTO, "MD5", wxPayConfig.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxRefundQueryDTO);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_REFUND_QUERY.getUrl(),xml);
            log.info("微信退款查询返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxRefundQueryVO.fromXML(responseContent,WxRefundQueryVO.class);
    }
}
