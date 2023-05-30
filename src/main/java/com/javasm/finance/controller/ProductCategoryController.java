package com.javasm.finance.controller;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.PageInfo;
import com.javasm.finance.entity.ProductCategory;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.service.ProductCategroyService;
import com.javasm.finance.service.impl.ProductCategroyServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/series/*")
public class ProductCategoryController extends BaseController {
    ProductCategroyService productCategroyService = new ProductCategroyServiceImpl();

    //查询产品系列数据
    protected void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //产品系列的名字(中英文)
        String name = req.getParameter("name");
        // 当前页
        Integer page = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.valueOf(pageStr);
        }
        // 每页显示的数据条数
        Integer pageSize = 5;
        String pageSizeStr = req.getParameter("pageSize");
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }
        //查询产品系列数据
        List<ProductCategory> productCategoryList = productCategroyService.queryProductCategory(page, pageSize, name);
        //设置响应信息
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (productCategoryList != null && productCategoryList.size()>0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 查询数据的总条数
            long total = productCategroyService.queryProductCategoryNum(name);
            //创建PageInfo对象
            PageInfo pageInfo = new PageInfo(page, pageSize, total);
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("productCategoryList", productCategoryList);
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            PageInfo pageInfo = new PageInfo(1, 5, 0);
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        }
        //写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }

    //添加数据
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String  productChannelStr= req.getParameter("productChannel");
        //类型转换
        Integer productChannel = null;
        if (productChannelStr != null && !"".equals(productChannelStr)) {
            productChannel = Integer.valueOf(productChannelStr);
        }
        String  productTypeStr= req.getParameter("productType");
        //类型转换
        Integer productType = null;
        if (productTypeStr != null && !"".equals(productTypeStr)) {
            productType = Integer.valueOf(productTypeStr);
        }
        String  productCategoryName= req.getParameter("productCategoryName");
        String  productEnCategoryName= req.getParameter("productEnCategoryName");
        //创建实参对象
        ProductCategory productCategory =
                new ProductCategory(productChannel,productType,productCategoryName,productEnCategoryName);
        //调用业务层执行添加操作
        int row = productCategroyService.addProductCategory(productCategory);
        //设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (row > 0) {
            entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        } else {
            entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
        }
        //写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    //根据系列编号显示修改数据
    protected void toEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String productCategoryIdStr = req.getParameter("productCategoryId");
        //类型转换
        Integer productCategoryId = null;
        if (productCategoryIdStr != null && !"".equals(productCategoryIdStr)) {
            productCategoryId = Integer.valueOf(productCategoryIdStr);
        }
        //调用业务层执行查询显示操作
        ProductCategory productCategory =
                productCategroyService.queryProductCategoryId(productCategoryId);
        //设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (productCategory != null && !"".equals(productCategory)) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(productCategory);
        }else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        //写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }

    //修改数据
    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String productCategoryIdStr = req.getParameter("productCategoryId");
        //类型转换
        Integer productCategoryId = null;
        if (productCategoryIdStr != null && !"".equals(productCategoryIdStr)) {
            productCategoryId = Integer.valueOf(productCategoryIdStr);
        }
        System.out.println(productCategoryId);
        String  productChannelStr= req.getParameter("productChannel");
        //类型转换
        Integer productChannel = null;
        if (productChannelStr != null && !"".equals(productChannelStr)) {
            productChannel = Integer.valueOf(productChannelStr);
        }
        String  productTypeStr= req.getParameter("productType");
        //类型转换
        Integer productType = null;
        if (productTypeStr != null && !"".equals(productTypeStr)) {
            productType = Integer.valueOf(productTypeStr);
        }
        String  productCategoryName= req.getParameter("productCategoryName");
        String  productEnCategoryName= req.getParameter("productEnCategoryName");
        String versionIdStr = req.getParameter("versionId");
        //类型转换
        Integer versionId = null;
        if (versionIdStr != null && !"".equals(versionIdStr)) {
            versionId = Integer.valueOf(versionIdStr);
        }
        //根据系列编号和版本号来判断是否被修改
        ProductCategory queryByMidAndVid =
                productCategroyService.queryByMidAndVid(productCategoryId, versionId);
        //创建修改的对象
        ProductCategory productCategory =
                new ProductCategory(productCategoryId, productType, productChannel,
                        productCategoryName, productEnCategoryName, versionId, null);
        System.out.println(productCategory);
        //设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (queryByMidAndVid != null) {//数据是最新数据
            //修改数据
            int row = productCategroyService.editProductCategory(productCategory);
            System.out.println(row);
            if (row > 0 ) {
                entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
            } else {
                entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
            }
        }else {//数据不是最新数据(被其他人修改过了)
            entity.setReturnCode(CodeAndMsg.DATA_ISUPDATE.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.DATA_ISUPDATE.getReturnMsg());
        }
        //写出响应数据
        RespDataHandleUtils.handleResp(resp,entity);
    }

}
