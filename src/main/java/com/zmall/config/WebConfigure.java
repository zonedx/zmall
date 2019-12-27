package com.zmall.config;

import com.zmall.controller.common.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author duanxin
 * @className: WebConfigure
 * @date 2019-12-27 14:54
 **/
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authorityInterceptor).addPathPatterns("/manager/**");
//        addInterceptors(registry);
    }


}
