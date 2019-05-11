package com.niezhiliang.simple.pay.vos;

import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/11 下午2:31
 */
@Data
public class AlipayRefundQueryVO {

    private String code;

    private String msg;

    private String out_trade_no;

    private String total_amount;

    private String refund_amount;

    private String trade_no;

    private String out_request_no;

}
