package com.javasm.finance.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//处理请求的服务类的超类-公共逻辑的处理代码
public class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取当前虚拟路径值
        String requestURI = req.getRequestURI();
        // 处理当前请求的方法的名字
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        // 通过反射找到对应的方法执行
        // 获取类对象
        Class<? extends BaseController> aClass = this.getClass();
        // 获取对应的方法：getDeclaredMethod()，获取本类中的方法，修饰词不限
        try {
            Method method = aClass.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
