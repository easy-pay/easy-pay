package com.niezhiliang.simple.pay.content;

import com.niezhiliang.simple.pay.strategy.PayStrategy;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 上午11:16
 */
public class PayContent<R,T> {

    private PayStrategy<R,T> payStrategy;

    public PayContent(PayStrategy<R, T> payStrategy) {
        this.payStrategy = payStrategy;
    }

    public R execute(T t) {

        return payStrategy.operate(t);
    }
}
