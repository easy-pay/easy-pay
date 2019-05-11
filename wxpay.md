### 微信支付调用文档

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