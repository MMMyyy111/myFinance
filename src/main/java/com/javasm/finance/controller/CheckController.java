package com.javasm.finance.controller;

import com.javasm.finance.dao.RecommendDao;
import com.javasm.finance.entity.*;
import com.javasm.finance.service.CheckService;
import com.javasm.finance.service.impl.CheckServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/check/*")
public class CheckController extends BaseController{
    public void queryCheckProd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        String productName = req.getParameter("productName");
        Integer auditStatus = null;
        String auditStatusStr = req.getParameter("auditStatus");
        if (auditStatusStr != null && !"".equals(auditStatusStr)) {
            auditStatus = Integer.valueOf(auditStatusStr);
        }
        Integer page = null;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.valueOf(pageStr);
        }
        Integer pageSize = null;
        String pageSizeStr = req.getParameter("pageSize");
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }
        CheckService checkService = new CheckServiceImpl();
        ReturnEntity entity = new ReturnEntity();
        List<Product> checkProdList = checkService.queryCheckProd(page, pageSize, productName, auditStatus, loginUser.getUserId());
        if(checkProdList!=null && checkProdList.size()>0){
            long totalNum = checkService.totalNum(productName, auditStatus, loginUser.getUserId());
            PageInfo pageInfo = new PageInfo(page, pageSize, totalNum);
            Map<String,Object> map = new HashMap<>();
            map.put("pageInfo",pageInfo);
            map.put("checkProdList",checkProdList);
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(map);
        }else {
            PageInfo pageInfo = new PageInfo(1, 5, 0);
            Map<String,Object> map = new HashMap<>();
            map.put("pageInfo",pageInfo);
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            entity.setReturnData(map);
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void queryProdById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        CheckService checkService = new CheckServiceImpl();
        Product prodById = checkService.queryById(productId);
        ReturnEntity entity = new ReturnEntity();
        if(prodById!=null){
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(prodById);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void editReject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        String versionIdStr = req.getParameter("versionId");
        Integer versionId = null;
        if( versionIdStr!=null && !"".equals(versionIdStr)){
            versionId = Integer.valueOf(versionIdStr);
        }
        CheckService checkService = new CheckServiceImpl();
        ReturnEntity entity = new ReturnEntity();
        int i = checkService.editAuditStatusReject(productId);
        if(i>0){
            entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        }else{
            entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
    public void editApprove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        CheckService checkService = new CheckServiceImpl();
        ReturnEntity entity = new ReturnEntity();
        int i = checkService.editAuditStatusApprove(productId);
        if(i>0){
            entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        }else{
            entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
}
