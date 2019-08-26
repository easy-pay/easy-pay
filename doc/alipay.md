### 支付宝支付调用文档(sdk version:3.7.26.ALL)

- [支付宝二维码生成方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#%E4%BA%8C%E7%BB%B4%E7%A0%81%E7%94%9F%E6%88%90)

- [支付宝H5支付方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#h5%E6%94%AF%E4%BB%98)

- [支付宝网站支付方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#%E7%94%B5%E8%84%91%E7%BD%91%E7%AB%99%E6%94%AF%E4%BB%98)

- [支付宝支付回调方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#%E6%94%AF%E4%BB%98%E5%9B%9E%E8%B0%83)

- [支付宝退款方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#%E9%80%80%E6%AC%BE)

- [支付宝退款查询方法](https://github.com/easy-pay/easy-pay/blob/master/doc/alipay.md#%E9%80%80%E6%AC%BE%E6%9F%A5%E8%AF%A2)

#### 二维码生成

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
```html
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
***

#### H5支付

> 通过调用h5支付的方式，获取到响应结果后，直接将结果返回给前端界面，前端通过` document.write(data)` 写到上下文中，就会把
支付页面渲染出来，自动调用本地的支付宝app来进行支付操作。

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| subject  |String|  必须  |   订单标题
| outTradeNo  |String|  必须  |  商户订单号
| totalAmount  |String|  必须  |  订单金额
| quitUrl  |String|  必须  |  支付途中退出返回的商户页面

##### 调用示例

```java
    @RequestMapping(value = "h5pay")
    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO) {

        return PayUtils.alipayH5Pay(aliPayH5PayDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/h5pay?totalAmount=0.01&subject=测试二维码支付&outTradeNo=999999999&quitUrl=www.test.com
```

##### Easy-Pay支付宝H5支付响应示例
```json
<form name="punchout_form" method="post" action="https://openapi.alipay.com/gateway.do?charset=utf-8&method=alipay.trade.wap.pay&sign=NHQ2sRXF0fpAMhbUpWPzRBlgTGScl5bGrZu3DoRYk0COuCJ3m9DeX27jkgPvjSXK6%2FeCR1Lu9edDOqSJtS1O67CTXWvlOTyHC%2Fxd6hBJ5mt1K1ig4kaKvuAy1kBKJR3e7P7Zd6o5qZrrlofzu9DSQeHrTFCFgZCo7xImbA6JTCxu%2Bqkln9JpK1UiJ2hTGzTLEljwsfvFeG6siuG8ocGjVLOzuMNtWN60FPJH9wiztq5PsKw6g%2BG659PsMNlTjfILI07dke3mfHgX8HPG1elnQaImz0Jqa9qAPzBw6v4OjKRZN1qKkDNACK7lwnOW1DsaAl22omqRC3PIk3%2Bsin142A%3D%3D&version=1.0&app_id=2018011101770628&sign_type=RSA2&timestamp=2019-08-26+15%3A16%3A18&alipay_sdk=alipay-sdk-java-3.7.26.ALL&format=JSON">
<input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;1566803732815&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;total_amount&quot;:&quot;0.01&quot;,&quot;quit_url&quot;:&quot;www.baidu.com&quot;,&quot;subject&quot;:&quot;测试支付&quot;}">
<input type="submit" value="立即支付" style="display:none" >
</form>
<script>document.forms[0].submit();</script>
```

***

#### 电脑网站支付

> 跳到支付宝官网支付。填充完订单数据以后，直接将浏览器的值替换成 http://127.0.0.1:9999/pcpay?totalAmount=0.01&subject=测试网址支付&outTradeNo=888888&body=订单测试描述
就会自动跳转到支付宝的官网支付。如果有不明白的请参考我的`demo`

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
```html
        填充完参数之后直接用这段代码来实现。
        var oriderId = $("#oriderId").val();
        var title = $("#title").val();
        var price = $("#price").val();
        var descript = $("#descript").val();

        window.location.href="http://127.0.0.1:9999/alipay/pcpay?totalAmount="+price+"&subject="+title+"&outTradeNo="+oriderId+"&body="+descript;
```

![演示gif](https://github.com/easy-pay/easy-pay/blob/master/doc/pcpay.gif)


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
***



#### 订单关闭

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |  商户订单号
| operatorId  |String|  可选  |   卖家端自定义的的操作员 ID

##### 调用示例

```java
    @RequestMapping(value = "closeorder")
    public AlipayCloseOrderVO closeorder(AlipayCloseOrderDTO alipayCloseOrderDTO) {

        return PayUtils.AlipayCloseOrder(alipayCloseOrderDTO);
    }
```

##### 浏览器访问示例
```html
127.0.0.1:9999/closeorder?outTradeNo=123456&operatorId=admin
```
***

#### 支付回调

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
***

#### 退款

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
```html
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
***

#### 退款查询


###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| outRequestNo  |String|  可选  |   退款单号，如果申请退款时没有传这个参数，则支付宝默认商户订单号和退款单号一样。就可以不传，如果退款也用的Easy-Pay的接口，则可以不传该参数

##### 浏览器访问示例
```html
127.0.0.1:9999/refundQuery?outTradeNo=88888
```

##### Easy-Pay响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| code  |String|  必须  |  返回结果响应码,成功为`10000`，其余均为异常状态码
| msg  |String|  必须  |   结果响应信息
| out_trade_no  |String|  必须  |   商家订单号
| total_amount  |String|  必须  |  订单金额
| refund_amount  |String|  必须  |  退款金额
| trade_no  |String|  必须  |  交易流水号
| out_request_no  |String|  必须  |  退款流水号



##### Easy-Pay响应示例
```json
{
    "code": "10000", 
    "msg": "Success", 
    "out_trade_no": "88888", 
    "total_amount": "0.01", 
    "refund_amount": "0.01", 
    "trade_no": "2019051122001416131036450964", 
    "out_request_no": "88888"
}
```

