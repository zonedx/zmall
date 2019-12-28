package com.zmall.common;

import com.zmall.controller.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ExcptionResolver
 * @Date 2019-10-13 10:37
 * @Author duanxin
 **/
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception", httpServletRequest.getRequestURI(), e);

        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof LogicException) {
            LogicException exception = (LogicException) e;

            //当使用的是jack2.x的时候使用MappingJackson2JsonView，当前版本为1.9
            modelAndView.addObject("status", exception.getCode());
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("data", e.toString());
        } else {
            modelAndView.addObject("status", ResultCode.ERROR);
            modelAndView.addObject("msg", "接口异常,详情请查看服务端日志信息");
            modelAndView.addObject("data", e.toString());
        }

        return modelAndView;
    }
}
