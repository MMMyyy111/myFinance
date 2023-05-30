package com.javasm.finance.util;

import com.alibaba.fastjson.JSON;
import com.javasm.finance.entity.ReturnEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RespDataHandleUtils {
    public static void handleResp(HttpServletResponse resp, ReturnEntity entity) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(entity));
        writer.flush();
        writer.close();
    }
}
