package com.niezhiliang.simple.pay.config;

import java.lang.annotation.*;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/7 下午4:07
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAlias {

    /**
     * 字段别名
     * @return
     */
    String alias() default "";

    /**
     * 属性是否必填
     * @return
     */
    boolean must() default false;
}
