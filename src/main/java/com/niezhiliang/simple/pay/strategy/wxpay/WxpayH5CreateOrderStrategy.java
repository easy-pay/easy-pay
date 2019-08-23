package com.niezhiliang.simple.pay.strategy.wxpay;

import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WxpayQrcodeDTO;
import com.niezhiliang.simple.pay.strategy.PayStrategy;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxpayH5CreateOrderVO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-08-23 14:40
 * H5统一下单
 */
@Slf4j
public class WxpayH5CreateOrderStrategy implements PayStrategy<WxpayH5CreateOrderVO, WxpayQrcodeDTO> {

    @Override
    public WxpayH5CreateOrderVO operate(WxpayQrcodeDTO wxQrcodeDTO) {
        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxQrcodeDTO.setAppId(wxPayConfig.getAppId());
        wxQrcodeDTO.setMchId(wxPayConfig.getMchId());
        wxQrcodeDTO.setTradeType("MWEB");
        wxQrcodeDTO.setNotifyUrl(wxPayConfig.getPayNotify());
        wxQrcodeDTO.setTotalFee(new BigDecimal(wxQrcodeDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()+"");
        wxQrcodeDTO.setNonceStr(wxQrcodeDTO.getOutTradeNo());
        wxQrcodeDTO.setSign(null);
        wxQrcodeDTO.setSign(SignUtils.createSign(wxQrcodeDTO,"MD5", wxPayConfig.getMchKey(), new String[0]));

        String xml = XmlUtils.toXML(wxQrcodeDTO);
        log.debug("微信二维码下单请求参数：{}", xml);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_QRCODE.getUrl(),xml);
            log.debug("微信二维码下单返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxpayH5CreateOrderVO.fromXML(responseContent,WxpayH5CreateOrderVO.class);
    }
}
