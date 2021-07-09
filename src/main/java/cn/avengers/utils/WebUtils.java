package cn.avengers.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {

    /**
     * 把Map中的值注入对应的JavaBean属性中
     * @param value
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T copyParamToBean(Map value, T bean){

        try {
            System.out.println("注入之前：" + bean);
            /**
             * 把所有请求的参数都注入到user对象中
             */
            BeanUtils.populate(bean, value);
            System.out.println("注入之后：" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 把字符串转换为int类型的数据
     */
    public static int parseInt(String strInt, int defaultValue){

        try{
            return Integer.parseInt(strInt);
        }catch (Exception e){

        }
        return defaultValue;

    }

}
