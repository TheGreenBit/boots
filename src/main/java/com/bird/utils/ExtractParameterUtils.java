package com.bird.utils;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class ExtractParameterUtils {

    public static <T> T extractFromHttpRequest(Class<T> tClass, HttpServletRequest httpServletRequest) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        if (parameterNames != null && parameterNames.hasMoreElements() && propertyDescriptors != null && propertyDescriptors.length > 0) {
            T t = tClass.newInstance();
            TypeConverter typeConverter = new SimpleTypeConverter();
            for (PropertyDescriptor pd : propertyDescriptors) {
                String parameter = httpServletRequest.getParameter(pd.getName());
                if (parameter != null) {
                    Object convert = typeConverter.convertIfNecessary(parameter, pd.getPropertyType());
                    if (convert != null) {
                        Method writeMethod = pd.getWriteMethod();
                        writeMethod.setAccessible(true);
                        writeMethod.invoke(t,convert);
                    }
                }
            }
            return t;
        }
        return null;

    }
}
