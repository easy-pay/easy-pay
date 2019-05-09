package com.niezhiliang.simple.pay.dto;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午3:20
 */
public class WXBaseDTO {
    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
