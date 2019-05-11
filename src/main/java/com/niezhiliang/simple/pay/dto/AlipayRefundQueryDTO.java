package com.niezhiliang.simple.pay.dto;

import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/11 下午2:29
 */
@Data
public class AlipayRefundQueryDTO {
    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 退款申请的单号
     */
    private String outRequestNo;
}
