package com.niezhiliang.simple.pay.utils;

import com.niezhiliang.simple.pay.config.FieldAlias;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午8:35
 */
public class ConfigUtils {

    public static void readProperties(Object obj) {

        try {
            InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream("application.properties");

            if (inputStream == null) {
                throw new FileNotFoundException("easy-pay : resources Folder do not contain file application.properties || application.yml");
            }
            Properties properties = new Properties();
            properties.load(inputStream);

            Class clazz = obj.getClass();

            Annotation fieldAlias = clazz.getAnnotation(FieldAlias.class);

            try {
                Method method = FieldAlias.class.getMethod("alias");
                String prefix = (String) method.invoke(fieldAlias);

                Field[] fields = clazz.getDeclaredFields();

                for(Field field : fields) {
                    int mod = field.getModifiers();
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }

                    if(field.isAnnotationPresent(FieldAlias.class)) {
                        Annotation annotation = field.getAnnotation(FieldAlias.class);

                        method = FieldAlias.class.getMethod("alias");

                        String alilas = (String) method.invoke(annotation);

                        method = FieldAlias.class.getMethod("must");

                        Boolean flag = (Boolean) method.invoke(annotation);

                        StringBuffer stringBuffer = new StringBuffer(prefix);
                        stringBuffer.append(".").append(alilas);

                        Object  value = properties.get(stringBuffer.toString());
                        if (flag) {
                            if (null == value) {
                                //TODO 抛异常
                            }
                        }
                        field.setAccessible(true);
                        field.set(obj,value == null ? null : value.toString() );
                    };

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
