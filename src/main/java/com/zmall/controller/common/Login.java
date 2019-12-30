package com.zmall.controller.common;

import com.zmall.common.Const;

import java.lang.annotation.*;

/**
 * 登录拦截注解，对登录自动校验。
 * 加在类上对整个类的路径有效，加在方法上对单个路径有效。
 * 加在方法上的优先级比加在类上的优先级高，
 * 意思是如果类上和方法上同样加了{@link Login}注解，只会使用方法上的。
 *
 * @author liujinrui
 * @date 2019/12/27 17:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Login {
    /**
     * 权限要求
     */
    int value() default Const.Role.ROLE_CUSTOMER;
}
