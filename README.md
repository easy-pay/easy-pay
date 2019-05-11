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

##### 调用示例

```java
    @RequestMapping(value = "qrcode")
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO qrcodeDTO) {

        return PayUtils.alipayQrcode(qrcodeDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/qrcode?totalAmount=0.01&subject=测试二维码支付&outTradeNo=999999999
```

##### Easy-Pay支付宝二维码响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| code  |String|  必须  |  返回结果响应码,成功为`10000`，其余均为异常状态码
| msg  |String|  必须  |   结果响应信息
| out_trade_no  |String|  必须  |   商家订单号(之前传的生成二维码的订单号)
| qr_code  |String|  必须  |  生成的支付二维码地址



##### Easy-Pay支付宝二维码响应示例
```json
{
    "code": "10000",
    "msg": "Success",
    "out_trade_no": "999999999",
    "qr_code": "https://qr.alipay.com/bax07482nvqubviekjut60b2"
}
```

##### 支付回调

###### 描述

支付回调是支付宝在买家支付成功之后，用来异步通知商家用户的当前支付状态，用户在接收到支付宝回调信息后，如果支付成功后必须返回一个`SUCCESS`告诉
支付宝方，我已经成功接收到了回调信息，不用再发送了，要是不给支付宝返回`SUCCESS`，支付宝会一直调用我们提供的回调接口，只是调用的时间间隔可能会
越来越长而已。总之，如果接收到了回调信息一定要响应`SUCCESS`给支付宝。

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |  商户订单号
| subject  |String|  必须  |   订单标题
| totalAmount  |String|  必须  |   订单金额

##### 调用示例

```java
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request){
        AlipayCallBackVO aliPayCallBackVO = PayUtils.alipayPayCallBack(request);

        //支付成功通过websocket将回调结果返回给前端，我们生产环境需要判断是否回调结果状态并改变数据库中订单的值
        if(aliPayCallBackVO.getTrade_status().equals(SUCCESS_PAY_STATUS)) {
            log.info('支付成功,这里处理支付成功后的逻辑');
        }
        //返回给支付宝回调的接口的'SUCCESS'已经封装好了,如果不是成功，则该值为'FAILER'
        return aliPayCallBackVO.getShouldResonse();
    }
```

##### Easy-Pay支付宝二维码响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| app_id  |String|  必须  |  商户的AppId
| buyer_id  |String|  必须  |   买家支付宝的id
| buyer_logon_id  |String|  必须  |  支付的登录账号
| gmt_create  |String|  必须  |  订单创建时间
| invoice_amount  |String|  必须  |  订单金额
| out_trade_no  |String|  必须  |  商家订单号
| seller_email  |String|  必须  | 商家支付宝账号
| shouldResonse  |String|  必须  |  回调应该响应给支付宝的值
| subject  |String|  必须  |  订单标题
| total_amount  |String|  必须  |  订单总金额
| trade_no  |String|  必须  |  支付宝交易订单号
| trade_status  |String|  必须  |  支付状态 TRADE_SUCCESS 支付成功

##### Easy-Pay支付宝回调响应示例
```json
{
	"app_id": "2018161101770628",
	"buyer_id": "2088312544416266",
	"buyer_logon_id": "159****0143",
	"gmt_create": 1557539365000,
	"invoice_amount": 0.01,
	"out_trade_no": "1557539356472",
	"seller_email": "xx@xx.cc",
	"shouldResonse": "SUCCESS",
	"subject": "支付宝二维码支付测试",
	"total_amount": 0.01,
	"trade_no": "2019051122001416131036487007",
	"trade_status": "TRADE_SUCCESS"
}
```


##### 退款

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| refundAmount  |String|  必须  |   退款金额(元)
| refundReason  |String|  必须  |   退款原因

##### 调用示例

```java
    @RequestMapping(value = "refund")
    public AlipayRefundVO refund(AlipayRefundDTO refundDTO) {
        return PayUtils.alipayRefund(refundDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/refund?refundAmount=0.01&refundReason=测试退款&outTradeNo=999999999
```

##### Easy-Pay响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| code  |String|  必须  |  返回结果响应码,成功为`10000`，其余均为异常状态码
| msg  |String|  必须  |   结果响应信息
| out_trade_no  |String|  必须  |   商家订单号(之前传的生成二维码的订单号)
| buyer_logon_id  |String|  必须  |  支付用户的登录id
| buyer_user_id  |String|  必须  |  支付用户在支付宝的用户id
| fund_change  |String|  必须  |  本次退款是否发生了资金变化
| gmt_refund_pay  |String|  必须  |  退款支付时间
| refund_fee  |String|  必须  |  退款总金额
| send_back_fee  |String|  必须  |  退回金额



##### Easy-Pay响应示例
```json
{
    "code": "10000",
    "msg": "Success",
    "out_trade_no": "999999999",
    "buyer_logon_id": "159******43",
    "buyer_user_id": "2088312544416136",
    "fund_change": "Y",
    "gmt_refund_pay": "2019-05-11 09:27:03",
    "refund_fee": 0.01,
    "send_back_fee": 0.01
}
```



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
