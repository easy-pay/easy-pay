package com.niezhiliang.simple.pay.strategy;

import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WXQrcodeDTO;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxPayQrcodeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午3:15
 * 微信二维码策略
 */
@Slf4j
public class WXQrcodeStrategy implements PayStrategy<WxPayQrcodeVO,WXQrcodeDTO>  {

    @Override
    public WxPayQrcodeVO operate(WXQrcodeDTO wxQrcodeDTO) {

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();
        wxQrcodeDTO.setAppid(wxPayConfig.getAppId());
        wxQrcodeDTO.setMch_id(wxPayConfig.getMchId());
        wxQrcodeDTO.setTrade_type(wxPayConfig.getTradeType());
        wxQrcodeDTO.setNotify_url(wxPayConfig.getPayNotify());
        wxQrcodeDTO.setTotal_fee(wxQrcodeDTO.getTotal_fee()*100);
        wxQrcodeDTO.setNonce_str(wxQrcodeDTO.getOut_trade_no());
        wxQrcodeDTO.setSpbill_create_ip("127.0.0.1");
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
        return WxPayQrcodeVO.fromXML(responseContent,WxPayQrcodeVO.class);
    }
}
