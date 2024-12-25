package com.test.filter;

import com.test.common.Result;
import com.test.util.WebUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理跨域请求的过滤器
 *
 */
@WebFilter("/*")
    public class CrosFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            System.out.println(request.getMethod());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE, HEAD");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
            // 如果是跨域预检请求,则直接在此响应200业务码
            if(request.getMethod().equalsIgnoreCase("OPTIONS")){
                WebUtil.writeJson(response, Result.ok(null));
            }else{
                // 非预检请求,放行即可
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        @Override
        public void destroy() {

        }
    }