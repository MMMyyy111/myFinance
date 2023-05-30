package com.javasm.finance.controller;

import com.javasm.finance.entity.CodeAndMsg;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.entity.PageInfo;
import com.javasm.finance.entity.ReturnEntity;
import com.javasm.finance.service.MenuService;
import com.javasm.finance.service.impl.MenuServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/menu/*")
public class MenuController extends BaseController {
    MenuService menuService = new MenuServiceImpl();

    //查询菜单数据
    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 菜单名称
        String menuName = req.getParameter("menuName");
        // 上级菜单编号
        Integer parentId = null;
        String parentIdStr = req.getParameter("parentId");
        if (parentIdStr != null && !"".equals(parentIdStr)) {
            parentId = Integer.valueOf(parentIdStr);
        }

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
        // 查询菜单数据
        List<Menu> menuList = menuService.queryMenu(page, pageSize, menuName, parentId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (menuList != null && menuList.size() > 0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 查询数据的总条数
            long total = menuService.queryMenuNum(menuName, parentId);
            // 创建PageInfo对象
            PageInfo pageInfo = new PageInfo(page, pageSize, total);
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("menuList", menuList);
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            PageInfo pageInfo = new PageInfo(1, 5, 0);
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    //查询一级菜单数据
    protected void queryOneMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取一级菜单数据
        List<Menu> oneMenuList = menuService.queryOneMenu();
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (oneMenuList != null && oneMenuList.size() > 0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(oneMenuList);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 增加菜单数据
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 接收请求数据
        String menuIdStr = req.getParameter("menuId");
        //类型转换
        Integer menuId = null;
        if (menuIdStr != null && !"".equals(menuIdStr)) {
            menuId = Integer.valueOf(menuIdStr);
        }
        String menuName = req.getParameter("menuName");
        String parentIdStr = req.getParameter("parentId");
        //类型转换
        Integer parentId = null;
        if (parentIdStr != null && !"".equals(parentIdStr)) {
            parentId = Integer.valueOf(parentIdStr);
        }
        String menuUrl = req.getParameter("menuUrl");
        String glyphicon = req.getParameter("glyphicon");
        // 根据菜单编号查询菜单数据
        Menu queryMenuId = menuService.queryMenuId(menuId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (queryMenuId != null) {
            // 菜单编号已被使用
            entity.setReturnCode(CodeAndMsg.ID_DUPLICATE.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.ID_DUPLICATE.getReturnMsg());
        } else {
            // 执行增加
            Menu menu = new Menu(menuId, menuName, parentId, null, menuUrl, glyphicon, null, null);
            int row = menuService.addMenu(menu);
            if (row > 0) {
                entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
            } else {
                entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
                entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
            }
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    //获取要修改的数据
    protected void toEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 接收请求数据
        String menuIdStr = req.getParameter("menuId");
        Integer menuId = null;
        if (menuIdStr != null && !"".equals(menuIdStr)) {
            menuId = Integer.valueOf(menuIdStr);
        }
        // 根据菜单编号查询菜单数据
        Menu menu = menuService.queryMenuId(menuId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (menu != null) {
            // 菜单编号已被使用
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(menu);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 修改菜单
    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 接收请求数据
        String menuIdStr = req.getParameter("menuId");
        //类型转换
        Integer menuId = null;
        if (menuIdStr != null && !"".equals(menuIdStr)) {
            menuId = Integer.valueOf(menuIdStr);
        }
        String menuName = req.getParameter("menuName");
        String parentIdStr = req.getParameter("parentId");
        //类型转换
        Integer parentId = null;
        if (parentIdStr != null && !"".equals(parentIdStr)) {
            parentId = Integer.valueOf(parentIdStr);
        }
        String menuUrl = req.getParameter("menuUrl");
        String glyphicon = req.getParameter("glyphicon");
        String versionIdStr = req.getParameter("versionId");
        //类型转换
        Integer versionId = null;
        if (versionIdStr != null && !"".equals(versionIdStr)) {
            versionId = Integer.valueOf(versionIdStr);
        }
        // 执行
        Menu menu = new Menu(menuId, menuName, parentId, null, menuUrl, glyphicon, versionId, null);
        // 根据菜单编号和版本编号查询数据是不是最新数据
        Menu queryByMidAndVid = menuService.queryByMidAndVid(menuId, versionId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        // 判断
        if (queryByMidAndVid != null) {//数据是最新数据
            // 执行修改操作
            int row = menuService.editMenu(menu);
            if (row > 0) {
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
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    //删除菜单
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求数据
        String menuIdStr = req.getParameter("menuId");
        //类型转换
        Integer menuId = null;
        if (menuIdStr != null && !"".equals(menuIdStr)) {
            menuId = Integer.valueOf(menuIdStr);
        }
        // 执行删除操作
        boolean isSuccess = menuService.deleteMenu(menuId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        // 判断
        if (isSuccess) {
            entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        }else {
            entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

}
