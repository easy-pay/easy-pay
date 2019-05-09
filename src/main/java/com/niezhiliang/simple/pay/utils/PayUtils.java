package com.niezhiliang.simple.pay.utils;

import com.niezhiliang.simple.pay.content.PayContent;
import com.niezhiliang.simple.pay.dto.*;
import com.niezhiliang.simple.pay.strategy.*;
import com.niezhiliang.simple.pay.vos.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 下午1:44
 */
public class PayUtils {


    /**
     * PC端支付下单 返回一个表单
     * @param pcPayDTO
     * @return
     */
    public static String pcPay(AlipayPcPayDTO pcPayDTO) {
        PayContent<String,AlipayPcPayDTO> payContent = new PayContent(new AliPayPcPayStrategy());
        return payContent.execute(pcPayDTO);
    }

    /**
     * 支付宝二维码生成
     * @param qrcodeDTO
     * @return
     */
    public static QrcodeVO alipayQrcode(AlipayQrcodeDTO qrcodeDTO) {
        PayContent<QrcodeVO,AlipayQrcodeDTO> payContent = new PayContent(new AlipayQrcodeStrategy());
        return payContent.execute(qrcodeDTO);
    }

    /**
     * 支付宝退款接口
     * @param alipayRefundDTO
     * @return
     */
    public static RefundVO alipayRefund(AlipayRefundDTO alipayRefundDTO) {
        PayContent<RefundVO,AlipayRefundDTO> payContent = new PayContent(new AliPayRefundStrategy());
        return payContent.execute(alipayRefundDTO);
    }

    /**
     * 支付宝支付回调
     * @param request
     * @return
     */
    public static Boolean alipayPayCallBack(HttpServletRequest request) {
        PayContent<Boolean,HttpServletRequest> payContent = new PayContent(new AliPayCallBackStrategy());
        return payContent.execute(request);

    }

    /**
     * 微信生成二维码
     * @param wxQrcodeDTO
     * @return
     */
    public static WxPayQrcodeVO wxpayQrcode(WXQrcodeDTO wxQrcodeDTO) {
        PayContent<WxPayQrcodeVO,WXQrcodeDTO> payContent = new PayContent(new WXQrcodeStrategy());
        return payContent.execute(wxQrcodeDTO);
    }

    /**
     * 微信支付回调
     * @param request
     * @return
     */
    public static WxCallBackVO wxpayNotify(HttpServletRequest request) {
        PayContent<WxCallBackVO,HttpServletRequest> payContent = new PayContent(new WxPayNotifyStrategy());
        return payContent.execute(request);
    }

    /**
     * 微信关闭订单
     * @param wxCloseOrderDTO
     * @return
     */
    public static WxPayCloseOrderVO wxpayCloseOrder(WXCloseOrderDTO wxCloseOrderDTO) {
        PayContent<WxPayCloseOrderVO,WXCloseOrderDTO> payContent = new PayContent(new WXCloseOrderStrategy());
        return payContent.execute(wxCloseOrderDTO);
    }

    /**
     * 微信退款
     * @param wxRefundDTO
     * @return
     */
    public static WxPayCloseOrderVO wxRefund(WXRefundDTO wxRefundDTO){
        PayContent<WxPayCloseOrderVO,WXRefundDTO> payContent = new PayContent(new WXRefundStrategy());
        return payContent.execute(wxRefundDTO);
    }

    /**
     * 微信退款查询
     * @param wxRefundQueryDTO
     * @return
     */
    public static WxRefundQueryVO  wxRefundQuery(WxRefundQueryDTO wxRefundQueryDTO) {
        PayContent<WxRefundQueryVO,WxRefundQueryDTO> payContent = new PayContent(new WxRefundQueryStrategy());
        return payContent.execute(wxRefundQueryDTO);
    }

    public static void main(String[] args) {
        int a = new Random().nextInt(100000000);
        WXQrcodeDTO dto = new WXQrcodeDTO();
        dto.setOut_trade_no("123456654321");
        dto.setBody("测试支付");
        dto.setTotal_fee(1);
        System.out.println(PayUtils.wxpayQrcode(dto));
    }

}
