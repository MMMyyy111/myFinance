package com.javasm.finance.controller;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.entity.User;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/logOut")
public class LogOutController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
// 从session对象上获取当前登录用户的权限数据
        HttpSession session = req.getSession();
        // 从session对象上移除指定值
        session.removeAttribute("loginMenuList");
        session.removeAttribute("loginUser");
        session.removeAttribute("loginUrlList");
        // 准备响应数据
        ReturnEntity entity = new ReturnEntity();

            entity.setReturnCode(CodeAndMsg.LOGOUT_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.LOGOUT_SUCCESS.getReturnMsg());
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }
}
