package com.javasm.finance.controller;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class UploadController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取文件对象数据
        Part userFile = req.getPart("userFile");
        // 获取文件名
        String fileName = userFile.getSubmittedFileName();
        // 设置保存文件的地址
        String realPath = req.getServletContext().getRealPath("/");
        String folderName = "upload/";
        // 执行保存文件操作
        userFile.write(realPath + folderName + fileName);
        // 获取保存文件地址
        String filePath = "http://localhost:8080/" + folderName + fileName;
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
        entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        entity.setReturnData(filePath);
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }
}
