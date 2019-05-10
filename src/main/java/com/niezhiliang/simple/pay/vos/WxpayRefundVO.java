package com.niezhiliang.simple.pay.vos;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/10 下午2:35
 */
@Data
@XStreamAlias("xml")
public class WxpayRefundVO extends BaseWxPayResult implements Serializable {

    /**
     * 商户交易订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 微信方交易订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户退款订单号
     */
    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    /**
     * 微信方退款订单号
     */
    @XStreamAlias("refund_id")
    private String refundId;

    /**
     * 退款金额
     */
    @XStreamAlias("refund_fee")
    private String refundFee;

    /**
     * 订单总金额
     */
    @XStreamAlias("total_fee")
    private String totalFee;
}
