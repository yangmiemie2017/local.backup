package com.strong.util;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import com.strong.web.controller.ApiServiceController;


public class ReflectionUtil {
    private static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	private static Logger logger = LoggerFactory.getLogger(ApiServiceController.class);    
    /**
     * Get all parameter names for a method
     * @param method
     * @return
     */
    public static String[] getParameterNames(Method method) {
        return parameterNameDiscoverer.getParameterNames(method);
    }

    public static String[] getMethodParams(String pkgeName, String methodName) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(pkgeName);
        Method[] methods = aClass.getMethods();
        String[] params = null;
        logger.info(String.format("getMethodParams:%1$s,%2$s",pkgeName,methodName));        
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                params = getParameterNames(method);
                if (params == null || params.length == 0) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("method：" + method.getName() + "()  ");
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) {
                        sb.append(" ,");
                    }
                    sb.append(params[i]);
                }
                logger.info(sb.toString());
                break;
            }
        }
        return params;
    }
    

}
