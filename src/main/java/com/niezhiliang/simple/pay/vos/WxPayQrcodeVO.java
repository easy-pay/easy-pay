package com.niezhiliang.simple.pay.vos;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午4:17
 */
@XStreamAlias("xml")
@Data
public class WxPayQrcodeVO extends BaseWxPayResult implements Serializable {

    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("prepay_id")
    private String prepayId;

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 二维码链接
     */
    @XStreamAlias("code_url")
    private String codeUrl;


    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
