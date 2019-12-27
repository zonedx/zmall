package com.zmall.controller.common;

import java.lang.annotation.*;

/**
 * Controller传参时，给用户加上此注解可以自动传入当前用户
 *
 * @author liujinrui
 * @date 2019/12/27 17:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CurrentUser {
}
