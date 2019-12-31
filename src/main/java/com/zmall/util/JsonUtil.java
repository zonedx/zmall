package com.zmall.util;

import com.google.common.collect.Lists;
import com.zmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName: JsonUtil
 * @Date 2019-10-08 17:03
 * @Author duanxin
 **/

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //影响序列化的行为
    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式统一为以下格式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error", e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }


    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setUsername("zone");
        user.setEmail("123456@163.com");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("zone2");
        user2.setEmail("1234562@163.com");

        String userJson = JsonUtil.obj2String(user);
        String userJsonPretty = JsonUtil.obj2StringPretty(user);

        log.info(userJson);
        log.info(userJsonPretty);

        List<User> userList = Lists.newArrayList();
        userList.add(user);
        userList.add(user2);

        String userListString = JsonUtil.obj2StringPretty(userList);
        log.info("=========");
        log.info(userListString);
        log.info("=========");
        //此处为大坑  泛型是List的时候 反序列化后list中的对象变成了LinkedList，导致没有对应对象的getXXX()方法了
        //List<User> userList1 = JsonUtil.string2Obj(userListString, List.class);
        List<User> userList1 = JsonUtil.string2Obj(userListString, new TypeReference<List<User>>() {
        });

        List<User> userList2 = JsonUtil.string2Obj(userListString,List.class,User.class);
        assert userList1 != null;
        log.info(userList1.toString());
        assert userList2 != null;
        log.info(userList2.toString());

        System.out.println("end");

    }

}
