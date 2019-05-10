package com.niezhiliang.simple.pay.vos;

import lombok.Data;

import java.util.Date;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 下午3:34AliPayCallBackStrategy.java
 * 支付宝回调参数
 */
@Data
public class AlipayCallBackVO {

    /**
     * 订单创建时间
     */
    private Date gmt_create;

    /**
     * 商家支付宝账号
     */
    private String seller_email;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 商家交易流水号
     */
    private String out_trade_no;

    /**
     * 支付的id
     */
    private String buyer_id;

    /**
     * 订单金额
     */
    private Double invoice_amount;

    /**
     * 支付状态 TRADE_SUCCESS 支付成功
     */
    private String trade_status;

    /**
     * 支付的登录账号
     */
    private String buyer_logon_id;

    /**
     * 支付宝交易流水号
     */
    private String trade_no;

    /**
     * 商户的AppId
     */
    private String app_id;

    /**
     * 订单总金额
     */
    private Double total_amount;

    /**
     * 响应给支付宝的字符串
     */
    private String shouldResonse = "FAILER";

}
