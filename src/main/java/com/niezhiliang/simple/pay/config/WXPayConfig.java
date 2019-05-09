package com.niezhiliang.simple.pay.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.niezhiliang.simple.pay.utils.ConfigUtils;
import com.niezhiliang.simple.pay.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;
import org.ho.yaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午1:51
 */
@Data
@ToString
@FieldAlias(alias = "wxpay")
public class WXPayConfig {

    private WXPayConfig() {
    }

    private static WXPayConfig wxPayConfig = null;

    private static final String WXPAY_PREFIX = "wxpay";

    /**
     * 众号appid
     */
    @FieldAlias(alias = "appId",must = true)
    private String appId;

    /**
     * 商户id
     */
    @FieldAlias(alias = "mchId",must = true)
    private String mchId;

    /**
     * 付api安全密钥
     */
    @FieldAlias(alias = "mchKey",must = true)
    private String mchKey;

    /**
     * 支付类型
     */
    @FieldAlias(alias = "tradeType",must = true)
    private String tradeType;

    /**
     * 支付回调
     */
    @FieldAlias(alias = "payNotify",must = true)
    private String payNotify;

    /**
     * 退款回调
     */
    @FieldAlias(alias = "payNotify",must = false)
    private String refundNotify;

    /**
     * 证书地址
     */
    @FieldAlias(alias = "payNotify",must = true)
    private String certName;

    public static WXPayConfig getInstance() {
        if (wxPayConfig == null) {
            wxPayConfig = new WXPayConfig();
        }
        //获取更目录下的application.yml文件
        InputStream inputStream = AlipayConfig.class.getClassLoader().getResourceAsStream("application.yml");
        //如果没有yml文件则去读取properties文件
        if (inputStream == null) {
            ConfigUtils.readProperties(wxPayConfig);
        } else {
            Map father = null;
            try {
                father = Yaml.loadType(inputStream, HashMap.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (father.containsKey(WXPAY_PREFIX)) {
                //填充微信支付配置
                Map<String,String> map  = (Map<String, String>) father.get(WXPAY_PREFIX);
                JsonUtils.mapToObject(map,wxPayConfig);
            }
        }
        return wxPayConfig;
    }
}
