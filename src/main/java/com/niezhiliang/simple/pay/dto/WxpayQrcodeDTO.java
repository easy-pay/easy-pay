package com.niezhiliang.simple.pay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午3:16
 * 微信二维码请求参数类
 */
@XStreamAlias("xml")
@Data
public class WxpayQrcodeDTO extends WxpayBaseDTO {

    /**
     *商品描述
     */
    @XStreamAlias("body")
    private String body;

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 标价金额
     */
    @XStreamAlias("total_fee")
    private String totalFee;

    /**
     * 终端IP
     */
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 异步通知地址
     */
    @XStreamAlias("notify_url")
    private String notifyUrl;
}
