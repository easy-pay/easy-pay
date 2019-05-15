### Easy-Pay

<img src="https://github.com/easy-pay/easy-pay/blob/master/logo.jpg" width="700" height="150" alt="logo"/>


### 注意事项

- 如果只想使用支付宝或微信`其中一方`支付方式，配置文件只需要配置微信或支付宝的配置无需两个支付方式都配置


### 各种框架使用Easy-Pay的Demo

- [SpringBoot使用Easy-Pay的Demo](https://github.com/easy-pay/spring-boot-easy-pay-demo)

![演示gif](https://github.com/easy-pay/spring-boot-easy-pay-demo/blob/master/src/main/resources/demo.png)





#### 使用前配置文件配置如下

- 在项目`pom.xml`文件中引入`Easy-Pay`的依赖，该依赖已经发布到了maven的中央仓库(还暂时未发布，忙完这阵子再发布)。

```
    <groupId>com.niezhiliang.simple.pay</groupId>
    <artifactId>easy-pay</artifactId>
    <version>1.0.0</version>
```

- 必须创建一个`application.yml`或者`application.properties`文件放在项目的根目录下

这是yml格式的配置文件

```yaml
#支付宝支付参数配置
alipay:
  #应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
  appId: '2018080866998'
  #商户私钥，您的PKCS8格式RSA2私钥
  privateKey: 
  publicKey: 
  #服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  notifyUrl: 'http://www.niezhiliang.com:9999/alipay/callback'
  #页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  returnUrl: 'http://www.niezhiliang.com:9999/success'
  #签名方式(固定值，无需修改)
  signType: RSA2
  #字符编码格式(固定值，无需修改)
  charset: utf-8
  #支付宝网关(固定值，无需修改)
  gatewayUrl: 'https://openapi.alipay.com/gateway.do'
  #保存支付日志的地址(该功能待实现)
  logPath: /tmp/
#微信支付参数配置
wxpay:
  #公众号appid
  appId: 
  #商户id
  mchId: 
  #支付api安全密钥
  mchKey: 
  #支付类型(固定值，无需修改)
  tradeType: 'NATIVE'
  #支付结果回调地址
  payNotify: 'http://www.niezhiliang.com:9999/wx/callback'
  #退款结果回调(该值暂时还未使用到，因为退款我并没有做回调，待以后完善吧)
  refundNotify:
  #项目根目录根目录下的证书名称(退款需要用到证书)
  certName: 'wx_pay_cert.p12'

```

- [支付宝支付使用文档](https://github.com/easy-pay/easy-pay/blob/master/alipay.md)

- [微信支付使用文档](https://github.com/easy-pay/easy-pay/blob/master/wxpay.md)

 有问题可以`微信`我

<img width="200" height="200" src="https://img-blog.csdn.net/20180605172659802?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MDgyMzA0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70"/>
