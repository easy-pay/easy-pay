package com.niezhiliang.simple.pay.config;

import com.niezhiliang.simple.pay.annos.Must;
import com.niezhiliang.simple.pay.exception.EasyPayException;
import org.ho.yaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/12/31 下午 19:06
 */
public class ConfFileReader {

    /** 配置文件 **/
    private static final String CONFIG_NAME = "easypay.yml";


    /**
     * 获取配置文件
     * @return
     */
    private static InputStream getConfFile() {
        InputStream inputStream = ConfFileReader.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
        if (inputStream == null) {
            throw new EasyPayException("not found config file：[easypay.yml] in classpath");
        }

        return inputStream;
    }

    /**
     * 参数填充
     */
    public static void fillParams (Object obj,String prefix) throws Exception {
        Map father = Yaml.loadType(getConfFile(), HashMap.class);
        if (father != null && father.containsKey(prefix)) {
            //填充支付宝支付配置
            Map<String,Object> configMap  = (Map<String, Object>) father.get(prefix);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                Object value = configMap.get(field.getName());
                if (value != null) {
                    field.set(obj,value.toString());
                }
            }
        }
    }

    /**
     * 参数校验
     * @param obj
     * @param prefix
     */
    public static void chkParams (Object obj,String prefix) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            //只有被Must注解修饰的参数才会做校验
            if (field.isAnnotationPresent(Must.class)) {
                Object value = field.get(obj);
                if (value == null) {
                    throw new EasyPayException("easypay.yml [" +prefix + "."+field.getName() + "] is null" );
                }
            }
        }
    }
}
