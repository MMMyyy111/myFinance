package com.javasm.finance.controller;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.entity.User;
import com.javasm.finance.util.CORSHandleUtils;
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

@WebServlet("/main")
public class MainController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从session对象上获取当前登录用户的权限数据
        HttpSession session = req.getSession();
        // 从session对象上获取当前登录用户的权限数据->侧栏权限菜单数据呈现
        List<Menu> loginMenuList= (List<Menu>) session.getAttribute("loginMenuList");
        // 准备响应数据
        ReturnEntity entity = new ReturnEntity();
        if (loginMenuList != null && loginMenuList.size()>0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 从session对象上获取当前登录用户的用户名->系统主界面呈现对应的登录用户名
            User loginUser = (User)session.getAttribute("loginUser");
            String loginUserName = loginUser.getUserName();
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("loginMenuList",loginMenuList);
            returnMap.put("loginUserName",loginUserName);
            entity.setReturnData(returnMap);
        }else {
            entity.setReturnCode(CodeAndMsg.NO_LOGIN.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.NO_LOGIN.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }
}
