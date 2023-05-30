package com.javasm.finance.controller;

import com.alibaba.fastjson.JSON;
import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.entity.User;
import com.javasm.finance.service.MenuService;
import com.javasm.finance.service.UserService;
import com.javasm.finance.service.impl.MenuServiceImpl;
import com.javasm.finance.service.impl.UserServiceImpl;
import com.javasm.finance.util.CORSHandleUtils;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收请求数据
        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");
        //调用业务层方法
        UserService userService = new UserServiceImpl();
        User loginUser = userService.queryLoginUser(userName, userPwd);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (loginUser != null) {
            entity.setReturnCode(CodeAndMsg.LOGIN_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.LOGIN_SUCCESS.getReturnMsg());
            // 向session对象上绑定值    多次请求数据共享
            HttpSession session = req.getSession();
            // 获取登录用户的权限数据
            MenuService menuService = new MenuServiceImpl();
            List<Menu> loginMenuList = menuService.queryMenuList(loginUser.getUserId());
            session.setAttribute("loginMenuList", loginMenuList);
            // 绑定用户数据(辅助判断用户是否已登录)
            session.setAttribute("loginUser",loginUser);
            // 绑定用户权限数据(辅助判断用户是否有权限)
            List<String> loginUrlList = userService.queryLoginUrl(loginUser.getUserId());
            session.setAttribute("loginUrlList",loginUrlList);
        } else {
            entity.setReturnCode(CodeAndMsg.LOGIN_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.LOGIN_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);

    }
}
