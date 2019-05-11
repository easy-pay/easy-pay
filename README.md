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

##### 电脑网站支付(跳到支付宝官网支付。该怎么跳转到支付宝官网，我自己也有点懵逼，这一块还在调整中。)

###### 描述

在调用`Easy-Pay`的pc端支付，会返回支付宝返回的一个支付`form表单`，会直接跳转到支付宝的官网进行支付，上面也有扫码支付。pc端支付比二维码支付
需要多一个配置，那就是`回调页面`，用户在支付宝官网支付成功后，支付宝会跳转到我们设置好的`回调页面`,该回调页面必须是外网能访问到的。

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |  商户订单号
| subject  |String|  必须  |   订单标题
| body  |String|  必须  |   订单描述
| totalAmount  |String|  必须  |   订单金额

##### 调用示例

```java
    @RequestMapping(value = "pcpay")
    public String pcPay(AlipayPcPayDTO pcPayDTO){
        return PayUtils.alpayPcPay(pcPayDTO);
    }
```

##### 浏览器访问示例
```js
127.0.0.1:9999/pcpay?totalAmount=0.01&subject=测试网址支付&outTradeNo=888888&body=订单测试描述
```

##### Easy-Pay网址支付响应示例
```html
<html>
 <head></head>
 <body>
  <form name="punchout_form" method="post" action="https://openapi.alipay.com/gateway.do?charset=utf-8&amp;method=alipay.trade.page.pay&amp;sign=T4H85tx7RNLoqIYEPKAiQBWzIjEeaMA%2Bz4JmGUkFiuW9ooD8IXtFZuX85SLnoPZt1K7fmGveaug4yStqZFOqkLQIW20KHfRH5ZgQcwyk7yJmP1WBTqZS0ZQT0w3TvEgo1hrg8ezCUcwGlzUzHVkZ3qS9p8z0SxfkwB9tooQvrDFSy%2F6ObfqF3w4N3GYFGTxyrhYRIo7Z1gyiVjQ8vxlMrci%2FuCd5mCFZgkO0wRpXlptDK3vLX7It4Wbhio4isP0JJ0bge%2F5cmx2DYKvxnL%2FrysfQsUqkhxmAaDIsT1UQscUxIgXB1KZyZKwgNePO8tIAg4DgVwrxUoaMaMYGX5jomg%3D%3D&amp;return_url=http%3A%2F%2Fwww.niezhiliang.com%3A9999%2Fsuccess&amp;notify_url=http%3A%2F%2Fwww.niezhiliang.com%3A9999%2Falipay%2Fcallback&amp;version=1.0&amp;app_id=2018011101770628&amp;sign_type=RSA2&amp;timestamp=2019-05-11+10%3A10%3A22&amp;alipay_sdk=alipay-sdk-java-3.7.26.ALL&amp;format=JSON"> 
   <input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;888888&quot;,&quot;body&quot;:&quot;这是订单描述&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;,&quot;total_amount&quot;:0.01,&quot;subject&quot;:&quot;支付宝二维码支付测试&quot;}" /> 
   <input type="submit" value="立即支付" style="display:none" /> 
  </form> 
  <script>document.forms[0].submit();</script>
 </body>
</html>
```


##### 支付回调

###### 描述

支付回调是支付宝在买家支付成功之后，用来异步通知商家用户的当前支付状态，用户在接收到支付宝回调信息后，如果支付成功后必须返回一个`SUCCESS`告诉
支付宝方，我已经成功接收到了回调信息，不用再发送了，要是不给支付宝返回`SUCCESS`，支付宝会一直调用我们提供的回调接口，只是调用的时间间隔可能会
越来越长而已。总之，如果接收到了回调信息一定要响应`SUCCESS`给支付宝。

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


##### Easy-Pay支付宝回调响应参数
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

##### 调用示例

```java
    @RequestMapping(value = "qrcode")
    public WxpayQrcodeVO wxQrcode(WxpayQrcodeDTO qrcodeDTO) {

        return PayUtils.wxpayQrcode(qrcodeDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/wx/qrcode?totalFee=0.01&body=测试微信二维码支付&outTradeNo=999999
```

##### Easy-Pay支付宝二维码响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| subAppId  |String|  否  |  
| subMchId  |String|  否  |  
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| prepayId  |String|  必须  |  预支付交易会话标识
| tradeType  |String|  必须  |  支付类型
| codeUrl  |String|  必须  |  生成的支付二维码地址



##### Easy-Pay微信二维码响应示例
```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxxx", 
    "nonceStr": "oCuq5OPXTyulNaIK", 
    "sign": "396591213376A190D880C5B834C85DC9", 
    "xmlString": "", 
    "prepayId": "wx1110210690920174040601bd2188238263", 
    "tradeType": "NATIVE", 
    "codeUrl": "weixin://wxpay/bizpayurl?pr=Wj2oCm7"
}
```

##### 微信支付回调

##### 描述

支付回调是买家支付成功之后，微信用来异步通知商家用户的当前支付状态，用户在接收到微信回调信息后，如果支付成功后必须返回一个`SUCCESS`告诉
微信方，我已经成功接收到了回调信息，不用再发送了，要是不给微信返回`SUCCESS`，微信会一直调用我们提供的回调接口，只是调用的时间间隔可能会
越来越长而已。总之，如果接收到了回调信息一定要响应`SUCCESS`给微信。

##### 调用示例

```java
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request) {

        WxpayCallBackVO wxpayCallBackVO = PayUtils.wxpayNotify(request);
        //判断验签是否通过并且支付结果是不是成功,这里
        if (wxpayCallBackVO.getSignResult() && wxpayCallBackVO.getResultCode().equals("SUCCESS")) {
            return "SUCCESS";
        }
        return "FAILER";
    }
```

##### Easy-Pay微信支付回调响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| appId  |String|  必须  |  公众号appid
| mchId  |String|  必须  |  商户的Id
| nonceStr  |String|  必须  |  随机字符串
| outTradeNo  |String|  必须  |  商户交易的流水号
| resultCode  |String|  必须  |  交易结果
| signResult  |String|  必须  |  验签结果(Easy-Pay会对微信返回的结果进行验签)
| timeEnd  |String|  必须  |  支付时间
| totalFee  |String|  必须  |  订单总金额
| transactionId  |String|  必须  |  微信方交易流水号


##### Easy-Pay微信支付回调响应示例
```json
{
    "appId": "wx9c2707e2093dee43", 
    "mchId": "1495387912", 
    "nonceStr": "999999999", 
    "outTradeNo": "999999999", 
    "resultCode": "SUCCESS", 
    "signResult": true, 
    "timeEnd": "20190511104301", 
    "totalFee": "0.01", 
    "transactionId": "4200000338201905113432771893"
}
```

##### 订单关闭

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号

##### 调用示例

```java
    @RequestMapping(value = "closeOrder")
    public WxpayCloseOrderVO closeOrder(WXCloseOrderDTO closeOrderDTO) {
        return PayUtils.wxpayCloseOrder(closeOrderDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/wx/closeOrder?outTradeNo=999999
```

##### Easy-Pay微信关闭订单响应参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "111111111111", 
    "mchId": "1111111111", 
    "nonceStr": "aBIpVLvtHg2c5A0F", 
    "sign": "34A3AB4D484CABC6B15CADA8976EF77A", 
    "xmlString": ""
}
```


##### 退款

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| outRefundNo  |String|  必须  |   商户退款单号
| totalFee  |String|  必须  |   订单金额
| refundFee  |String|  必须  |   退款金额

##### 调用示例

```java
    @RequestMapping(value = "refund")
    public WxpayRefundVO wxRefund(WxpayRefundDTO wxpayRefundDTO) {
        return PayUtils.wxRefund(wxpayRefundDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/wx/refund?totalFee=0.01&refundFee=0.01&outTradeNo=999999999&outRefundNo=999999999
```
##### Easy-Pay微信退款响应参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| outTradeNo  |String|  必须  | 商户交易流水号
| transactionId  |String|  必须  | 微信方交易流水号
| outRefundNo  |String|  必须  | 商户交易退款流水号
| refundId  |String|  必须  | 微信方交易退款流水号
| refundFee  |String|  必须  | 退款金额
| totalFee  |String|  必须  | 订单总金额

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxx", 
    "nonceStr": "H5NcN0nv4FpByk2R", 
    "sign": "B1670926D1C9D819060DD1E502E4B131", 
    "xmlString": "", 
    "outTradeNo": "999999999", 
    "transactionId": "4200000338201905113432771893", 
    "outRefundNo": "999999999", 
    "refundId": "50000500372019051109519075276", 
    "refundFee": "0.01", 
    "totalFee": "0.01"
}
```


##### 退款查询

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号

##### 调用示例

```java
    @RequestMapping(value = "refundQuery")
    public WxpayRefundQueryVO refundQuery(WxpayRefundQueryDTO refundQueryDTO) {
        return PayUtils.wxRefundQuery(refundQueryDTO);
    }
```
##### 浏览器访问示例
```js
127.0.0.1:9999/wx/refundQuery?outTradeNo=999999999
```
##### Easy-Pay微信退款查询响应参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| outTradeNo  |String|  必须  | 商户交易流水号
| refundFee  |String|  必须  | 退款金额
| outRefundNo  |String|  必须  | 商户交易退款流水号
| refundCount  |String|  必须  | 退款次数
| cashFee  |String|  必须  | 退回金额

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxx", 
    "nonceStr": "uX6L4XoVaage5SQc", 
    "sign": "24A379FE304D8ED3FF7E755C6F07846D", 
    "xmlString": "", 
    "outTradeNo": "999999999", 
    "refundFee": 0.01, 
    "refundCount": 1, 
    "cashFee": 0.01
}
```
