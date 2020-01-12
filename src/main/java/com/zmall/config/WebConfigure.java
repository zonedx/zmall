package com.zmall.config;

import com.zmall.controller.common.interceptor.LoginResolver;
import com.zmall.controller.common.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author duanxin
 * @className: WebConfigure
 * @date 2019-12-27 14:54
 **/
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    private AuthorityInterceptor authorityInterceptor;
    private LoginResolver loginResolver;

    @Autowired
    public WebConfigure(AuthorityInterceptor authorityInterceptor, LoginResolver loginResolver) {
        this.authorityInterceptor = authorityInterceptor;
        this.loginResolver = loginResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authorityInterceptor).addPathPatterns("/manage/**");
        registry.addInterceptor(loginResolver);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginResolver);
    }
}
