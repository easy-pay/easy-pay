package com.niezhiliang.simple.pay.vos;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午4:18
 */
@Data
@XStreamAlias("xml")
public class WxRefundQueryVO extends BaseWxPayResult implements Serializable {

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 退款金额(分)
     */
    @XStreamAlias("refund_fee")
    private Integer refundFee;

    /**
     * 退款笔数
     */
    @XStreamAlias("refund_count")
    private Integer refundCount;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 现金支付金额(分)
     */
    @XStreamAlias("cash_fee")
    private Integer cashFee;

}
