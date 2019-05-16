package com.niezhiliang.simple.pay.vos;


import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:26
 */
@Data
public class AlipayQrcodeVO {

    /**
     * 返回的状态码
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 交易的流水号
     */
    private String out_trade_no;

    /**
     * 生成二维码的内容
     */
    private String qr_code;

    /**
     * 错误码
     */
    private String sub_code;

    /**
     * 错误信息
     */
    private String sub_msg;
}
