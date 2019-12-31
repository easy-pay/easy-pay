package com.niezhiliang.simple.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.niezhiliang.simple.pay.annos.Must;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:46
 */
@Data
@ToString
public class AlipayConfig {

    private AlipayConfig() {
    }

    private static AlipayConfig alipayConfig = null;

    private static final String ALIPAY_PREFIX = "alipay";

    private static final String YAML_NAME = "easypay.yml";

    /**
     * 应用ID,您的APPID 收款账号既是您的APPID对应支付宝账号
     */
    @Must
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @Must
    private String privateKey;

    /**
     * 支付宝公钥 https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    @Must
    private String publicKey;

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    @Must
    private  String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    private  String returnUrl;

    /**
     * 签名方式
     */
    @Must
    private  String signType = "RSA2";

    /**
     * 字符编码格式
     */
    @Must
    private  String charset = "utf-8";

    /**
     * 支付宝网关
     */
    @Must
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 日志存放
     */
    private String logPath = "/tmp/";


    public static AlipayConfig getInstance() {
        if (alipayConfig != null) {
           return alipayConfig;
        }
        alipayConfig = new AlipayConfig();
        try {
            //参数填充
            ConfFileReader.fillParams(alipayConfig,ALIPAY_PREFIX);
            ConfFileReader.chkParams(alipayConfig,ALIPAY_PREFIX);
            //这里是解决读配置文件输出科学计数的问题，有了这段代码配置文件appId就不用双引号啦
            if (alipayConfig.getAppId() != null) {
                alipayConfig.setAppId(new BigDecimal(alipayConfig.getAppId()).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        getInstance();
    }
}
