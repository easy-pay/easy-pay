package com.niezhiliang.simple.pay.utils;

import com.thoughtworks.xstream.XStream;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午2:49
 */
public class XmlUtils {

    public static String toXML(Object object) {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(object.getClass());
        return xstream.toXML(object);
    }

}
