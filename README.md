### Easy-Pay

让支付变得更简单


### 支付宝

##### 二维码生成

###### 请求参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号（不可重复，重复就会显示二维码失效）
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