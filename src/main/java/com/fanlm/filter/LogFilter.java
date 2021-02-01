package com.fanlm.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @program: java8_test
 * @description: testTagFilter
 * @author: flm
 * @create: 2021-01-22 15:08
 **/
public class LogFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("request start");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("request end");
    }
}
