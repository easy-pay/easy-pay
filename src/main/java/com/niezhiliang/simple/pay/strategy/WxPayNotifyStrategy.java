package com.niezhiliang.simple.pay.strategy;


import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.util.SignUtils;
import com.niezhiliang.simple.pay.config.WXPayConfig;
import com.niezhiliang.simple.pay.vos.WxCallBackVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/7 下午2:23
 */
@Slf4j
public class WxPayNotifyStrategy  implements PayStrategy<WxCallBackVO,HttpServletRequest> {


    @Override
    public WxCallBackVO operate(HttpServletRequest request) {

        String xmlData = getRequestXml(request);
        log.info(xmlData);
        WxPayOrderNotifyResult wxPayOrderNotifyResult =
                WxPayOrderNotifyResult.fromXML(xmlData,WxPayOrderNotifyResult.class);

        WXPayConfig wxPayConfig = WXPayConfig.getInstance();

        WxCallBackVO wxCallBackVO = new WxCallBackVO();
        wxCallBackVO.setAppId(wxPayOrderNotifyResult.getAppid());
        wxCallBackVO.setOutTradeNo(wxPayOrderNotifyResult.getOutTradeNo());
        wxCallBackVO.setTransactionId(wxPayOrderNotifyResult.getTransactionId());
        wxCallBackVO.setNonceStr(wxPayOrderNotifyResult.getNonceStr());
        wxCallBackVO.setMchId(wxPayOrderNotifyResult.getMchId());
        wxCallBackVO.setResultCode(wxPayOrderNotifyResult.getResultCode());
        wxCallBackVO.setTotalFee(wxPayOrderNotifyResult.getTotalFee());
        wxCallBackVO.setTimeEnd(wxPayOrderNotifyResult.getTimeEnd());

        boolean result = SignUtils.checkSign(wxPayOrderNotifyResult,"MD5",wxPayConfig.getMchKey());
        wxCallBackVO.setSignResult(result);

        return wxCallBackVO;
    }

    /**
     * 获取支付和退款回调参数
     * @return
     */
    private String getRequestXml(HttpServletRequest request) {
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return  new String(outSteam.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
