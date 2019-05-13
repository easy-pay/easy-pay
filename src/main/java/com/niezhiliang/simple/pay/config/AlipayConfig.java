package com.niezhiliang.simple.pay.config;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:46
 */
@FieldAlias(alias = "alipay",must = true)
@Data
@ToString
public class AlipayConfig {

    private AlipayConfig() {
    }

    private static AlipayConfig alipayConfig = null;

    private static final String ALIPAY_PREFIX = "alipay";

    /**
     * 应用ID,您的APPID 收款账号既是您的APPID对应支付宝账号
     */
    @FieldAlias(alias = "appId",must = true)
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @FieldAlias(alias = "privateKey",must = true)
    private String privateKey;

    /**
     * 支付宝公钥 https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    @FieldAlias(alias = "publicKey",must = true)
    private String publicKey;

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    @FieldAlias(alias = "notifyUrl",must = true)
    private  String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    @FieldAlias(alias = "returnUrl")
    private  String returnUrl;

    /**
     * 签名方式
     */
    @FieldAlias(alias = "signType",must = true)
    private  String signType;

    /**
     * 字符编码格式
     */
    @FieldAlias(alias = "charset",must = true)
    private  String charset;

    /**
     * 支付宝网关
     */
    @FieldAlias(alias = "gatewayUrl",must = true)
    private String gatewayUrl;

    /**
     * 日志存放
     */
    @FieldAlias(alias = "gatewayUrl")
    private String logPath;


    public static AlipayConfig getInstance() {
        if (alipayConfig != null) {
           return alipayConfig;
        }
        alipayConfig = new AlipayConfig();
        //获取更目录下的application.yml文件
        InputStream inputStream = AlipayConfig.class.getClassLoader().getResourceAsStream("application.yml");

        //如果没有yml文件则去读取properties文件
        if (inputStream == null) {
            ConfigUtils.readProperties(alipayConfig);
        } else {
            try {
                Map father = Yaml.loadType(inputStream, HashMap.class);
                if (father != null && father.containsKey(ALIPAY_PREFIX)) {
                    //填充支付宝支付配置
                    Map<String,String> map  = (Map<String, String>) father.get(ALIPAY_PREFIX);
                    JsonUtils.mapToObject(map,alipayConfig);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //这里是解决读配置文件输出科学计数的问题，有了这段代码配置文件appId就不用双引号啦
        if (alipayConfig.getAppId() != null) {
            alipayConfig.setAppId(new BigDecimal(alipayConfig.getAppId()).toString());
        }
        return alipayConfig;
    }

    /**
     * 获取AlipayClient对象
     * @return
     */
    public static AlipayClient getAlipayClient() {

        AlipayConfig alipayConfig = getInstance();

        AlipayClient alipayClient =
                new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(),
                        "JSON", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        return alipayClient;

    }

    public static void main(String[] args) {
        AlipayConfig alipayConfig =  AlipayConfig.getInstance();
        System.out.println(alipayConfig);
    }
}
