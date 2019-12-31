package com.zmall.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @ClassName: PropertiesUtils
 * @Date 2019-09-15 23:24
 * @Author duanxin
 **/

@Component
@PropertySource("classpath:application.yml")
public class PropertiesUtils {

    private static Environment env;


    @Autowired
    public void setEnv(Environment env){
        PropertiesUtils.env = env;
    }

    public static String getProperty(String key) {
        String value = env.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = env.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static Integer getIntegerProperty(String key) {
        String value = env.getProperty(key.trim());
        Integer valueInt = 0;
        if (StringUtils.isBlank(value)) {
            return valueInt;
        }
        valueInt = Integer.parseInt(value);
        return valueInt;
    }

    public static Integer getIntegerProperty(String key, String defaultValue) {
        String value = env.getProperty(key.trim());
        int valueInt;
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        valueInt = Integer.parseInt(value);
        return valueInt;
    }

    public static Boolean getBooleanProperty(String key) {
        String value = env.getProperty(key.trim());
        Boolean valueBool = null;
        if (StringUtils.isBlank(value)) {
            return valueBool;
        }
        valueBool = Boolean.parseBoolean(value);
        return valueBool;
    }

    public static Boolean getBooleanProperty(String key, String defaultValue) {
        String value = env.getProperty(key.trim());
        Boolean valueBool ;
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        valueBool = Boolean.parseBoolean(value);
        return valueBool;
    }

}
