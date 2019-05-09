package com.niezhiliang.simple.pay.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 下午2:45
 */
public class JsonUtils {


    /**
     * 格式化请求的json字符串对象
     * @param obj
     * @return
     */
    public static String jsonFormat(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        Map<String,Object> map = new HashMap<String, Object>(fields.length);

        for (Field field : fields ) {
            field.setAccessible(true);
            try {
                map.put(humpToLine(field.getName()),field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * map集合转对象
     * @param map
     * @param obj
     * @return
     */
    public static Object mapToObject(Map map,Object obj) {
        if(map == null) {
            return null;
        }
        try {
            Field [] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod)||Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj,map.get(field.getName()) == null ? null :map.get(field.getName()).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 驼峰命名转下划线命名
     * @param str
     * @return
     */
    private static String humpToLine(String str){
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 将异步通知的参数转化为Map
     * @return
     */
    public static Map<String, String> requestToMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name =  iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
