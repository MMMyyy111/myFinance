package com.javasm.finance.controller;

import com.javasm.finance.entity.*;
import com.javasm.finance.service.impl.BasicServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/basic/*")
public class ProductBasicController extends BaseController {
    BasicServiceImpl basicService = new BasicServiceImpl();

    //查询出数据并展示
    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取登录用户名字
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        String userName = loginUser.getUserName();
        //产品名称 productName

        String productName = req.getParameter("productName");
        //   产品系列Id productCategoryId
        String productCategoryIdStr = req.getParameter("productCategoryId");
        Integer productCategoryId=null;
        if (productCategoryIdStr != null && !"".equals(productCategoryIdStr)) {
            productCategoryId = Integer.valueOf(productCategoryIdStr);
        }
        // 审核状态码 auditStatus
        String auditStatusStr = req.getParameter("auditStatus");
        Integer auditStatus=null;
        if (auditStatusStr != null && !"".equals(auditStatusStr)) {
            auditStatus = Integer.valueOf(auditStatusStr);
        }
        //接受当前页
        Integer page = null;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.valueOf(pageStr);
        }
        //页面大小
        Integer pageSize = null;
        String pageSizeStr = req.getParameter("pageSize");
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }
        List<Product> productList = basicService.query(userName,productName,productCategoryId,auditStatus ,page, pageSize);
        ReturnEntity entity = new ReturnEntity();
        if (productList != null && productList.size() > 0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 查询数据的总条数
            long total = basicService.queryNum(userName,productName,productCategoryId,auditStatus);
            // 创建PageInfo对象
            PageInfo pageInfo = new PageInfo(page, pageSize, total);
            HashMap<String, Object> map = new HashMap<>();
            map.put("productList", productList);
            map.put("pageInfo", pageInfo);
            entity.setReturnData(map);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            PageInfo pageInfo = new PageInfo(1, 5, 0);
            HashMap<String, Object> map = new HashMap<>();
            map.put("pageInfo", pageInfo);
            entity.setReturnData(map);
        }
        RespDataHandleUtils.handleResp(resp, entity);
    }

    protected void queryCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ProductCategory> productCategories = basicService.queryCategory();
        ReturnEntity entity = new ReturnEntity();
        if (productCategories.size() > 0 && productCategories != null) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(productCategories);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
}
