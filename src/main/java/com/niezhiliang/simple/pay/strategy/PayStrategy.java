package com.niezhiliang.simple.pay.strategy;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:06
 */
public interface PayStrategy<R,T> {

    /**
     * 支付策略抽象
     * @param t
     * @return
     */
    R operate(T t);
}
