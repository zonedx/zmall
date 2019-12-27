package com.zmall.controller.common;

import java.lang.annotation.*;

/**
 * 登录拦截注解，对登录自动校验
 *
 * @author liujinrui
 * @date 2019/12/27 17:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Login {

}
