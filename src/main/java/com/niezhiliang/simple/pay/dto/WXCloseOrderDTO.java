package com.niezhiliang.simple.pay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:14
 */
@Data
@XStreamAlias("xml")
public class WXCloseOrderDTO extends WxpayBaseDTO {

    /**
     * 商户交易订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
}
