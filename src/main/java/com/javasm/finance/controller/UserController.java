package com.javasm.finance.controller;

import com.javasm.finance.entity.*;
import com.javasm.finance.service.MenuService;
import com.javasm.finance.service.UserService;
import com.javasm.finance.service.impl.MenuServiceImpl;
import com.javasm.finance.service.impl.UserServiceImpl;
import com.javasm.finance.util.RespDataHandleUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*")
public class UserController extends BaseController {
    UserService userService = new UserServiceImpl();

    //查询用户数据
    protected void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //用户编号
        Integer userId = null;
        String userIdStr = req.getParameter("userId");
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        //用户名称
        String userName = req.getParameter("userName");
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
        //查询用户数据
        List<User> userList = userService.queryUser(page, pageSize, userId, userName);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (userList != null && userList.size() > 0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 查询数据的总条数
            long total = userService.queryUserNum(userId, userName);
            //创建PageInfo对象
            PageInfo pageInfo = new PageInfo(page, pageSize, total);
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("userList", userList);
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
            PageInfo pageInfo = new PageInfo(1, 5, 0);
            // 创建Map集合
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("pageInfo", pageInfo);
            entity.setReturnData(returnMap);
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 增加用户数据
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userName = req.getParameter("userName");
        String roleIdStr = req.getParameter("roleId");
        //类型转换
        Integer roleId = null;
        if (roleIdStr != null && !"".equals(roleIdStr)) {
            roleId = Integer.valueOf(roleIdStr);
        }
        String regTime = req.getParameter("regTime");
        String isValidStr = req.getParameter("isValid");
        //类型转换
        Integer isValid = null;
        if (isValidStr != null && !"".equals(isValidStr)) {
            isValid = Integer.valueOf(isValidStr);
        }
        String headImg = req.getParameter("headImg");
        //创建要添加的对象
        User user = new User(userName, roleId, regTime, isValid, headImg);
        //执行添加操作
        int row = userService.addUser(user);
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
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 显示要修改的用户数据
    protected void toEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userIdStr = req.getParameter("userId");
        //类型转换
        Integer userId = null;
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        //根据菜单编号查询菜单数据
        User user = userService.queryUserId(userId);
        //设置响应数据
        ReturnEntity entity = new ReturnEntity();
        if (user != null && !"".equals(user)) {
            // 菜单编号已被使用
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            entity.setReturnData(user);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 修改用户数据
    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userIdStr = req.getParameter("userId");
        Integer userId = null;
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        String userName = req.getParameter("userName");
        String roleIdStr = req.getParameter("roleId");
        //类型转换
        Integer roleId = null;
        if (roleIdStr != null && !"".equals(roleIdStr)) {
            roleId = Integer.valueOf(roleIdStr);
        }
        String regTime = req.getParameter("regTime");
        String isValidStr = req.getParameter("isValid");
        //类型转换
        Integer isValid = null;
        if (isValidStr != null && !"".equals(isValidStr)) {
            isValid = Integer.valueOf(isValidStr);
        }
        String headImg = req.getParameter("headImg");
        String versionIdStr = req.getParameter("versionId");
        //类型转换
        Integer versionId = null;
        if (versionIdStr != null && !"".equals(versionIdStr)) {
            versionId = Integer.valueOf(versionIdStr);
        }
        //创建要添加的对象
        User user = new User(userId,userName,"1234",roleId,regTime,"null",isValid, headImg,versionId);
        // 根据菜单编号和版本编号查询数据是不是最新数据
        User queryByMidAndVid = userService.queryByMidAndVid(userId, versionId);
        //设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (queryByMidAndVid != null) {//数据是最新数据
            // 执行修改操作
            int row = userService.editUser(user);
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

    // 删除用户数据
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userIdStr = req.getParameter("userId");
        //类型转换
        Integer userId = null;
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        // 执行删除操作
        boolean row = userService.deleteUser(userId);
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (row) {
            entity.setReturnCode(CodeAndMsg.OPERATE_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_SUCCESS.getReturnMsg());
        } else {
            entity.setReturnCode(CodeAndMsg.OPERATE_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.OPERATE_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 查询所有的菜单数据和某些用户已有的权限菜单编号
    protected void queryUserMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userIdStr = req.getParameter("userId");
        //类型转换
        Integer userId = null;
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        // 查询所有的菜单数据
        MenuService menuService = new MenuServiceImpl();
        List<Menu> menuList = menuService.queryByLevel();
        // 设置响应数据
        ReturnEntity entity = new ReturnEntity();
        //判断
        if (menuList != null && menuList.size()>0) {
            entity.setReturnCode(CodeAndMsg.QUERY_SUCCESS.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_SUCCESS.getReturnMsg());
            // 查询该用户原有的权限菜单编号
            String menuIdStr = userService.queryMenuIdStr(userId);
            //创建Map集合
            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("menuList",menuList);
            if (menuIdStr != null && !"".equals(menuIdStr)) {
                returnMap.put("menuIdStr",menuIdStr);
            }
            entity.setReturnData(returnMap);
        } else {
            entity.setReturnCode(CodeAndMsg.QUERY_FAILURED.getReturnCode());
            entity.setReturnMsg(CodeAndMsg.QUERY_FAILURED.getReturnMsg());
        }
        // 写出响应数据
        RespDataHandleUtils.handleResp(resp, entity);
    }

    // 查询所有的菜单数据和某些用户已有的权限菜单编号
    protected void saveUserMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求数据
        String userIdStr = req.getParameter("userId");
        //类型转换
        Integer userId = null;
        if (userIdStr != null && !"".equals(userIdStr)) {
            userId = Integer.valueOf(userIdStr);
        }
        // 菜单编号
        String userMenuIdStr = req.getParameter("userMenuIdStr");
        if (userMenuIdStr != null && !"".equals(userMenuIdStr)) {
             String[] userMenuIdStrArr = userMenuIdStr.split(",");
             Object[][] userMenu = new Object[userMenuIdStrArr.length][2];
            for (int i = 0; i < userMenuIdStrArr.length; i++) {
                userMenu[i][0] = userId;
                userMenu[i][1] = Integer.valueOf(userMenuIdStrArr[i]);
            }
            boolean iSsuccess = userService.isSuccess(userId, userMenu);
            // 设置响应数据
            ReturnEntity entity = new ReturnEntity();
            //判断
            if (iSsuccess) {
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
