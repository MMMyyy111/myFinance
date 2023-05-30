package com.javasm.finance.service.impl;

import com.javasm.finance.dao.UserDao;
import com.javasm.finance.dao.impl.UserDaoImpl;
import com.javasm.finance.entity.User;
import com.javasm.finance.service.UserService;
import com.javasm.finance.util.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User queryLoginUser(String userName, String userPwd) {
        User user = null;
        try{
            user = userDao.queryLoginUser(userName, userPwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public List<String> queryLoginUrl(Integer userId) {
        List<String> LoginUrlList = null;
        try{
            LoginUrlList = userDao.queryLoginUrl(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return LoginUrlList;
    }

    @Override
    public List<User> queryUser(Integer page, Integer pageSize, Integer userId, String userName) {
        List<User> userList = null;
        try{
            userList = userDao.queryUser(page,pageSize,userId,userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    @Override
    public long queryUserNum(Integer userId, String userName) {
        long total = 0;
        try{
            total = userDao.queryUserNum(userId,userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    @Override
    public int addUser(User user) {
        int row = 0;
        try{
            row = userDao.addUser(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    @Override
    public User queryUserId(Integer userId) {
        User user = null;
        try{
            user = userDao.queryUserId(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User queryByMidAndVid(Integer userId, Integer versionId) {
        User user = null;
        try{
            user = userDao.queryByMidAndVid(userId,versionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public int editUser(User user) {
        int row = 0;
        try{
            row = userDao.editUser(user);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        boolean isSuccess = false;
        Connection conn = DbUtils.getConn();
        try {
            // 关闭事务自动提交
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            userDao.deleteUser(conn,userId);
            List<Integer> menuIdList = userDao.queryMenuIdList(userId);
            if (menuIdList != null && menuIdList.size()>0) {
                userDao.deleteUserMenu(conn,userId);
            }
            // 事务提交
            org.apache.commons.dbutils.DbUtils.commitAndClose(conn);
            isSuccess = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            // 事务回退
            org.apache.commons.dbutils.DbUtils.rollbackAndCloseQuietly(conn);
        }
        return isSuccess;
    }

    @Override
    public String queryMenuIdStr(Integer userId) {
        String userMenuIdStr = "";
        try{
            List<Integer> menuIdList = userDao.queryMenuIdList(userId);
            for (int i = 0; i < menuIdList.size(); i++) {
                // 获取二级菜单编号(过滤一级菜单编号)
//                if (menuIdList.get(i) >1000) {
                    userMenuIdStr += menuIdList.get(i);
                    if (i < menuIdList.size()-1) {
                        userMenuIdStr += ",";
                    }
//                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userMenuIdStr;
    }

    @Override
    public boolean isSuccess(Integer userId, Object[][] userMenu) {
        boolean isSuccess = false;
        Connection conn = DbUtils.getConn();
        //关闭事务
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            List<Integer> menuIdList = userDao.queryMenuIdList(userId);
            if (menuIdList != null && menuIdList.size()>0) {
                userDao.deleteUserMenu(conn,userId);
            }
            // 事务提交
            userDao.addUserMenu(userMenu, conn);
            org.apache.commons.dbutils.DbUtils.commitAndClose(conn);
            isSuccess = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            // 事务回退
            org.apache.commons.dbutils.DbUtils.rollbackAndCloseQuietly(conn);
        }
        return isSuccess;
    }
}
