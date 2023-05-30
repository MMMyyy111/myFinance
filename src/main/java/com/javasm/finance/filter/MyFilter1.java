package com.javasm.finance.filter;

import com.javasm.finance.util.CORSHandleUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class MyFilter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换类型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        // 处理跨域请求
        CORSHandleUtils.handleCors(resp);
        //设置编码格式
        req.setCharacterEncoding("utf-8");
        // 放行代码
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
