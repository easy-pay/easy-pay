package com.niezhiliang.simple.pay.dto;

import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 上午11:37
 * 退款的参数类
 */
@Data
public class AlipayRefundDTO {

    /**
     * 退款金额
     */
    private Double refundAmount;

    /**
     * 退款订单号
     */
    private String outTradeNo;

    /**
     * 退款理由
     */
    private String refundReason;
}
