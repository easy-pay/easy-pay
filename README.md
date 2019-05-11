### Easy-Pay

<img src="https://github.com/easy-pay/easy-pay/blob/master/logo.jpg" width="700" height="150" alt="logo"/>



### 各种框架使用Easy-Pay的Demo

- [SpringBoot使用Easy-Pay的Demo](https://github.com/easy-pay/spring-boot-easy-pay-demo)



#### 使用前配置文件配置如下

- 在项目`pom.xml`文件中引入`Easy-Pay`的依赖，该依赖已经发布到了maven的中央仓库。

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
  appId: 
  #商户私钥，您的PKCS8格式RSA2私钥
  privateKey: 
  publicKey: 
  #服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  notifyUrl: 'http://www.niezhiliang.com:9999/alipay/callback'
  #页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  returnUrl: 'http://www.niezhiliang.com:9999/success'
  #签名方式
  signType: RSA2
  #字符编码格式
  charset: utf-8
  #支付宝网关
  gatewayUrl: 'https://openapi.alipay.com/gateway.do'
  #保存支付日志的地址(该功能待实现)
  logPath: /tmp/
wxpay:
  #公众号appid
  appId: 
  #商户id
  mchId: 
  #支付api安全密钥
  mchKey: 
  #支付类型
  tradeType: 'NATIVE'
  #支付结果回调地址
  payNotify: 'http://www.niezhiliang.com:9999/wx/callback'
  #退款结果回调
  refundNotify:
  #项目根目录根目录下的证书名称
  certName: 'wx_pay_cert.p12'

```

### 支付宝

##### 二维码生成

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |  商户订单号
| subject  |String|  必须  |   订单标题
| totalAmount  |String|  必须  |   订单金额



##### 退款

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| refundAmount  |String|  必须  |   退款金额(元)
| refundReason  |String|  必须  |   退款原因



### 微信

##### 二维码生成

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| body  |String|  必须  |   订单描述
| totalFee  |String|  必须  |   订单金额


##### 订单关闭

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号


##### 退款

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| outRefundNo  |String|  必须  |   商户退款单号
| totalFee  |String|  必须  |   订单金额
| refundFee  |String|  必须  |   退款金额


##### 退款查询

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号






- appid  

不加 '' 会自动解析成dublod类型
