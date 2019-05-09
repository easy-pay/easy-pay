package com.niezhiliang.simple.pay.vos;

import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 上午11:41
 */
@Data
public class RefundVO {

    /**
     * 返回的状态码
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 交易的流水号
     */
    private String out_trade_no;

    /**
     * 买家支付宝账号
     */
    private String buyer_logon_id;

    /**
     * 买家在支付宝的用户id
     */
    private String buyer_user_id;

    /**
     * 本次退款是否发生了资金变化
     */
    private String fund_change;

    /**
     * 退款支付时间
     */
    private String gmt_refund_pay;

    /**
     * 退款金额
     */
    private Double refund_fee;

    /**
     * 退回金额
     */
    private Double send_back_fee;
}
