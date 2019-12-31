package com.zmall.controller.common;

import com.zmall.common.Const;
import com.zmall.pojo.User;
import com.zmall.util.CookieUtils;
import com.zmall.util.JsonUtils;
import com.zmall.util.RedisPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户执行某项操作后自动重置登录时间
 * @ClassName: SessionExpireFilter
 * @Date 2019-10-10 17:30
 * @Author duanxin
 **/
@Component
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String loginToken = CookieUtils.readLoginToken(request);
        if (StringUtils.isNotEmpty(loginToken)){
            //判断loginToken是否为空或者""
            //不为空符合条件继续拿user信息

            String userJsonStr = RedisPoolUtils.get(loginToken);
            User user = JsonUtils.string2Obj(userJsonStr,User.class);
            if (user != null){
                //如果user不为空，则重置session的时间
                RedisPoolUtils.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
