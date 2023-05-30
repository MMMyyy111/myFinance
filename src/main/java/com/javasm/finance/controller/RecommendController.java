package com.javasm.finance.controller;

import com.javasm.finance.entity.*;
import com.javasm.finance.service.RecommendService;
import com.javasm.finance.service.impl.RecommendServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/recommend/*")
public class RecommendController extends BaseController {

    public void queryRecommend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pageStr = req.getParameter("page");
        Integer page = 1;
        if(pageStr!=null && !pageStr.equals("")){
            page = Integer.valueOf(pageStr);
        }
        String pageSizeStr = req.getParameter("pageSize");
        Integer pageSize = 5;
        if(pageSizeStr!=null && !pageSizeStr.equals("")){
            pageSize = Integer.valueOf(pageSizeStr);
        }
        String productName = req.getParameter("productName");
        RecommendService recommendService = new RecommendServiceImpl();
        List<ProductRecommend> recommendList = recommendService.queryRecommend(page, pageSize, productName);
        ReturnEntity entity = new ReturnEntity();
        Map<String, Object> map = new HashMap<>();
        if(recommendList!=null && recommendList.size()>0){
            long total = recommendService.recommendTotal(productName);
            PageInfo pageInfo = new PageInfo(page, pageSize, total);
            map.put("recommendList",recommendList);
            map.put("pageInfo",pageInfo);
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(map);
        }else{
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            PageInfo pageInfo = new PageInfo(page, pageSize, 0);
            map.put("pageInfo",pageInfo);
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
    public void queryNotRecommend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RecommendService recommendService = new RecommendServiceImpl();
        List<Product> productsNotRecom = recommendService.queryNotRecom();
        ReturnEntity entity = new ReturnEntity();
        if(productsNotRecom!=null && productsNotRecom.size()>0){
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(productsNotRecom);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void addRecommend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !"".equals(productIdStr)){
            productId = Integer.valueOf(productIdStr);
        }
        String recommendRateStr = req.getParameter("recommendRate");
        Integer recommendRate = null;
        if(recommendRateStr!=null && !"".equals(recommendRateStr)){
            recommendRate = Integer.valueOf(recommendRateStr);
        }
        String isFirstStr = req.getParameter("isFirst");
        Integer isFirst = null;
        if(isFirstStr!=null && !"".equals(isFirstStr)){
            isFirst = Integer.valueOf(isFirstStr);
        }
        String isOnlineStr = req.getParameter("isOnline");
        Integer isOnline = null;
        if( isOnlineStr!=null && !"".equals(isOnlineStr)){
            isOnline = Integer.valueOf(isOnlineStr);
        }
        String isVisibleCustomerStr = req.getParameter("isVisibleCustomer");
        Integer isVisibleCustomer = null;
        if( isVisibleCustomerStr!=null && !"".equals(isVisibleCustomerStr)){
            isVisibleCustomer = Integer.valueOf(isVisibleCustomerStr);
        }
        String recommendReason = req.getParameter("recommendReason");
        ProductRecommend productRecommend = new ProductRecommend(productId, null, null, recommendRate, isFirst, isOnline, isVisibleCustomer, recommendReason,null);
        System.out.println(productRecommend);
        RecommendService recommendService = new RecommendServiceImpl();
        ProductRecommend queryRecomById = recommendService.queryRecomById(productId);
        ReturnEntity entity = new ReturnEntity();
        if(queryRecomById!=null){
            entity.setReturnCode(CodeAndMsg.DATA_ISUPDATE.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.DATA_ISUPDATE.getReturnMsg());
        }else {
            int i = recommendService.addRecommend(productRecommend);
            System.out.println(i);
            if(i>0){
                entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
            }else {
                entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
            }
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
    public void queryRecomById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        RecommendService recommendService = new RecommendServiceImpl();
        ProductRecommend productRecommend = recommendService.queryRecomById(productId);
        ReturnEntity entity = new ReturnEntity();
        if(productRecommend!=null){
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(productRecommend);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }
    public void editRecommend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !"".equals(productIdStr)){
            productId = Integer.valueOf(productIdStr);
        }
        String recommendRateStr = req.getParameter("recommendRate");
        Integer recommendRate = null;
        if(recommendRateStr!=null && !"".equals(recommendRateStr)){
            recommendRate = Integer.valueOf(recommendRateStr);
        }
        String isFirstStr = req.getParameter("isFirst");
        Integer isFirst = null;
        if(isFirstStr!=null && !"".equals(isFirstStr)){
            isFirst = Integer.valueOf(isFirstStr);
        }
        String isOnlineStr = req.getParameter("isOnline");
        Integer isOnline = null;
        if( isOnlineStr!=null && !"".equals(isOnlineStr)){
            isOnline = Integer.valueOf(isOnlineStr);
        }
        String isVisibleCustomerStr = req.getParameter("isVisibleCustomer");
        Integer isVisibleCustomer = null;
        if( isVisibleCustomerStr!=null && !"".equals(isVisibleCustomerStr)){
            isVisibleCustomer = Integer.valueOf(isVisibleCustomerStr);
        }
        String versionIdStr = req.getParameter("versionId");
        Integer versionId = null;
        if( versionIdStr!=null && !"".equals(versionIdStr)){
            versionId = Integer.valueOf(versionIdStr);
        }
        String recommendReason = req.getParameter("recommendReason");
        RecommendService recommendService = new RecommendServiceImpl();
        ProductRecommend recomByPidVid = recommendService.queryRecomByPidVid(productId, versionId);
        ProductRecommend productRecommend = new ProductRecommend(productId, null, null, recommendRate, isFirst, isOnline, isVisibleCustomer, recommendReason,versionId);
        ReturnEntity entity = new ReturnEntity();
        if(recomByPidVid!=null){
            int i = recommendService.editRecommend(productRecommend);
            System.out.println(productRecommend);
            System.out.println(i);
            if(i>0){
                entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
            }else {
                entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
            }
        }else{
            entity.setReturnCode(CodeAndMsg.DATA_ISUPDATE.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.DATA_ISUPDATE.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void querySeriesProd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        RecommendService recommendService = new RecommendServiceImpl();
        List<Product> seriesProd = recommendService.querySeriesProd(productId);
        ReturnEntity entity = new ReturnEntity();
        if(seriesProd != null && seriesProd.size()>0){
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(seriesProd);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void queryLinkedId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        RecommendService recommendService = new RecommendServiceImpl();
        List<Integer> linkedIdList = recommendService.queryLinkedId(productId);
        ReturnEntity entity = new ReturnEntity();
        if(linkedIdList != null && linkedIdList.size()>0){
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(linkedIdList);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        RespDataHandleUtils.handleResp(resp,entity);
    }

    public void executeLinkBuy(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        Integer productId = null;
        if(productIdStr!=null && !productIdStr.equals("")){
            productId = Integer.valueOf(productIdStr);
        }
        String linkedIdStr = req.getParameter("linkedId");
        System.out.println(productId);
        System.out.println(linkedIdStr);
        RecommendService recommendService = new RecommendServiceImpl();
        if(linkedIdStr!=null && !"".equals(linkedIdStr)){
            String[] linkedIdArr = linkedIdStr.split(",");
            Object[][] linkedProd = new Object[linkedIdArr.length][2];
            for(int i = 0; i < linkedIdArr.length;i++){
                linkedProd[i][0] = productId;
                linkedProd[i][1] = linkedIdArr[i];
                System.out.println(linkedProd[i]);
            }
            boolean b = recommendService.isSuccess(productId, linkedProd);
            ReturnEntity entity = new ReturnEntity();
            System.out.println(b);
            //判断
            if (b) {
                entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
            } else {
                entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
            }
            // 写出响应数据
            RespDataHandleUtils.handleResp(resp, entity);
        }
    }
}
