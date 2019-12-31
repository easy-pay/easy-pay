package com.niezhiliang.simple.pay.config;

import com.niezhiliang.simple.pay.annos.Must;
import lombok.Data;
import lombok.ToString;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/12/31 下午17:47
 */
@Data
@ToString
public class WXPayConfig {

    private WXPayConfig() {
    }

    private static WXPayConfig wxPayConfig = null;

    private static final String WXPAY_PREFIX = "wxpay";

    /**
     * 众号appid
     */
    @Must
    private String appId;

    /**
     * 商户id
     */
    @Must
    private String mchId;

    /**
     * 付api安全密钥
     */
    @Must
    private String mchKey;

    /**
     * 支付类型
     */
    @Must
    private String tradeType = "NATIVE";

    /**
     * 支付回调
     */
    @Must
    private String payNotify;

    /**
     * 退款回调
     */
    private String refundNotify;

    /**
     * 证书地址
     */
    private String certName;

    public static WXPayConfig getInstance() {
        if (wxPayConfig != null) {
            return wxPayConfig;
        }
        wxPayConfig = new WXPayConfig();
        try {
            //参数填充
            ConfFileReader.fillParams(wxPayConfig,WXPAY_PREFIX);
            //参数校验
            ConfFileReader.chkParams(wxPayConfig,WXPAY_PREFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPayConfig;
    }
}
