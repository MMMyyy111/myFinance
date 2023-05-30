package com.javasm.finance.filter;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class MyFilter3 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换类型
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取请求路径
        String servletPath = req.getServletPath();
        // 获取用户数据
        HttpSession session = req.getSession();
        List<String> loginUrlList = (List<String>) session.getAttribute("loginUrlList");
        // 判断
        if ("/login".equals(servletPath) || "/main".equals(servletPath)
                || "/logOut".equals(servletPath) || "/upload".equals(servletPath)
                || servletPath.endsWith(".jpg")) {//白名单(html、css、js、img、后端登录请求路径等)
            //放行
            chain.doFilter(req, resp);
        } else {
            // 判断有无权限
            if (loginUrlList.contains(servletPath)) {
                // 有权限 放行
                chain.doFilter(req, resp);
            } else {
                // 无权限
                ReturnEntity entity = new ReturnEntity();
                entity.setReturnCode(CodeAndMsg.NO_AUTH.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.NO_AUTH.getReturnMsg());
                // 写出响应数据
                RespDataHandleUtils.handleResp(resp, entity);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
