package com.niezhiliang.simple.pay.strategy;

import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.APIURLENUMS;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.dto.WXCloseOrderDTO;
import com.niezhiliang.simple.pay.utils.HttpUtils;
import com.niezhiliang.simple.pay.utils.XmlUtils;
import com.niezhiliang.simple.pay.vos.WxPayCloseOrderVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 上午10:17
 * 微信关闭订单
 */
@Slf4j
public class WXCloseOrderStrategy implements PayStrategy<WxPayCloseOrderVO,WXCloseOrderDTO> {

    @Override
    public WxPayCloseOrderVO operate(WXCloseOrderDTO wxCloseOrderDTO) {
        log.info("微信关闭订单号：{}", wxCloseOrderDTO.getOutTradeNo());
        wxCloseOrderDTO.setAppid(WXPayConfig.getInstance().getAppId());
        wxCloseOrderDTO.setMch_id(WXPayConfig.getInstance().getMchId());
        wxCloseOrderDTO.setNonce_str(wxCloseOrderDTO.getOutTradeNo());
        wxCloseOrderDTO.setSign(null);

        wxCloseOrderDTO.setSign(SignUtils.createSign(wxCloseOrderDTO, "MD5", WXPayConfig.getInstance().getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxCloseOrderDTO);
        String responseContent = null;
        try {
            responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_CLOSE_ORDER.getUrl(),xml);
            log.info("微信关闭订单返回参数：{}", responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxPayCloseOrderVO.fromXML(responseContent,WxPayCloseOrderVO.class);
    }
}
