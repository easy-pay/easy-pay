package com.niezhiliang.simple.pay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:38
 * 微信
 */
@XStreamAlias("xml")
@Data
public class WXRefundDTO extends WXBaseDTO {

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("refund_fee")
    private String refundFee;

    @XStreamAlias("notify_url")
    private String refundNotifyUrl;

}
