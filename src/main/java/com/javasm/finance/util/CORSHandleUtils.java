package com.javasm.finance.util;

import javax.servlet.http.HttpServletResponse;

public class CORSHandleUtils {
    public static void handleCors(HttpServletResponse resp){
        // 允许哪个客户端访问
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // 允许客户端使用哪些请求方法访问我：如下*号可换成：'get'或'get,post'等
        resp.setHeader("Access-Control-Allow-Methods", "*");
        // 重新预检验跨域的缓存时间 (s)
        resp.setHeader("Access-Control-Max-Age", "3600");
        // 允许跨域的请求头
        resp.setHeader("Access-Control-Allow-Headers", "*");
        // 允许客户端发送跨域请求时，携带Cookie信息
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
