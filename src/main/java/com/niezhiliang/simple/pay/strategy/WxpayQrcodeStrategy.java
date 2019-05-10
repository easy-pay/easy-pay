package com.niezhiliang.simple.pay.strategy;

import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WxpayQrcodeDTO;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxpayQrcodeVO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午3:15
 * 微信二维码策略
 */
@Slf4j
public class WxpayQrcodeStrategy implements PayStrategy<WxpayQrcodeVO,WxpayQrcodeDTO>  {

    @Override
    public WxpayQrcodeVO operate(WxpayQrcodeDTO wxQrcodeDTO) {

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxQrcodeDTO.setAppId(wxPayConfig.getAppId());
        wxQrcodeDTO.setMchId(wxPayConfig.getMchId());
        wxQrcodeDTO.setTradeType(wxPayConfig.getTradeType());
        wxQrcodeDTO.setNotifyUrl(wxPayConfig.getPayNotify());
        wxQrcodeDTO.setTotalFee(new BigDecimal(wxQrcodeDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()+"");
        wxQrcodeDTO.setNonceStr(wxQrcodeDTO.getOutTradeNo());
        wxQrcodeDTO.setSpbillCreateIp("127.0.0.1");
        wxQrcodeDTO.setSign(null);
        wxQrcodeDTO.setSign(SignUtils.createSign(wxQrcodeDTO,"MD5", wxPayConfig.getMchKey(), new String[0]));

        String xml = XmlUtils.toXML(wxQrcodeDTO);
        log.info("微信二维码下单请求参数：{}", xml);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_QRCODE.getUrl(),xml);
            log.info("微信二维码下单返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxpayQrcodeVO.fromXML(responseContent,WxpayQrcodeVO.class);
    }
}
