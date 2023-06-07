package com.example.redissentinel.filter;

import com.example.redissentinel.config.LimitedFlow;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class LimitedFlowFilter implements Filter {

    @Resource
    private LimitedFlow limitedFlow;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (limitedFlow.getToken()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.error("限制访问");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
