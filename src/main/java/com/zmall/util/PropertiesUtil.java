package com.zmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @ClassName: PropertiesUtil
 * @Date 2019-09-15 23:24
 * @Author duanxin
 **/

@Component
//@PropertySource("classpath:application.yml")
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    //静态代码块只加载一次，静态代码块>普通代码块>构造代码块
    static {
        String fileName = "application-dev.yml";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static Integer getIntegerProperty(String key) {
        String value = props.getProperty(key.trim());
        Integer valueInt = 0;
        if (StringUtils.isBlank(value)) {
            return valueInt;
        }
        valueInt = Integer.parseInt(value);
        return valueInt;
    }

    public static Integer getIntegerProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        Integer valueInt = 0;
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        valueInt = Integer.parseInt(value);
        return valueInt;
    }

    public static Boolean getBooleanProperty(String key) {
        String value = props.getProperty(key.trim());
        Boolean valueBool = null;
        if (StringUtils.isBlank(value)) {
            return valueBool;
        }
        valueBool = Boolean.parseBoolean(value);
        return valueBool;
    }

    public static Boolean getBooleanProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        Boolean valueBool ;
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        valueBool = Boolean.parseBoolean(value);
        return valueBool;
    }

}
