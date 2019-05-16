package com.niezhiliang.simple.pay.dto;

import lombok.Data;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/15 下午2:20
 */
@Data
public class AlipayCloseOrderDTO {

    /**
     * 交易流水号，不可重复
     */
    private String outTradeNo;

    /**
     * 卖家端自定义的的操作员 ID
     */
    private String operatorId;
}
