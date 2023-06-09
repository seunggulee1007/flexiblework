package com.secommon.separtners.infra.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle ( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        log.info( "request url : {}", request.getRequestURI() );
        return true;
    }

    @Override
    public void postHandle ( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
        log.info( "response status:{}", response.getStatus() );
    }

    @Override
    public void afterCompletion ( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) throws Exception {

    }
}
