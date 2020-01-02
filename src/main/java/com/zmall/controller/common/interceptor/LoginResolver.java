package com.zmall.controller.common.interceptor;

import com.zmall.common.LogicException;
import com.zmall.common.ResponseCode;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.User;
import com.zmall.util.CookieUtils;
import com.zmall.util.JsonUtils;
import com.zmall.util.RedisPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理{@link Login}注解和{@link CurrentUser}注解
 *
 * @author liujinrui
 * @since 2019/12/27 17:54
 */
@Component
public class LoginResolver extends HandlerInterceptorAdapter implements HandlerMethodArgumentResolver {
    /** 暂时存放User对象 */
    private final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            final User user = getUser(request);
            Login login;
            login = method.getMethod().getAnnotation(Login.class);
            if (login == null) {
                login = method.getBeanType().getAnnotation(Login.class);
            }
            if (login != null) {
                int role = login.value();
                if (user == null) {
                    throw new LogicException(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
                }
                if (user.getRole() == null || user.getRole() < role) {
                    throw new LogicException(ResponseCode.NO_PERMISSION.getCode(), ResponseCode.NO_PERMISSION.getDesc());
                }
                userThreadLocal.set(user);
            }

            if (user != null && userThreadLocal.get() == null){
                userThreadLocal.set(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        userThreadLocal.remove();
    }

    private User getUser(HttpServletRequest request) {
        String loginToken = CookieUtils.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return null;
        }
        String userJsonStr = RedisPoolUtils.get(loginToken);
        return JsonUtils.string2Obj(userJsonStr, User.class);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class)
                && methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        return userThreadLocal.get();
    }
}
