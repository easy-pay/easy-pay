package com.niezhiliang.simple.pay.config;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 */
public enum APIURLENUMS {
    //统一下单
    API_URL_QRCODE("https://api.mch.weixin.qq.com/pay/unifiedorder"),
    //退款接口
    API_URL_REFUND("https://api.mch.weixin.qq.com/secapi/pay/refund"),
    //退款查询
    API_URL_REFUND_QUERY("https://api.mch.weixin.qq.com/pay/refundquery"),
    //关闭订单
    API_URL_CLOSE_ORDER("https://api.mch.weixin.qq.com/pay/closeorder"),
    ;
    private String url;

    APIURLENUMS(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
