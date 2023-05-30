package com.javasm.finance.dao;

import com.javasm.finance.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface UserDao {

    //根据用户名和密码查询登录的用户数据
    User queryLoginUser(String userName,String userPwd) throws SQLException;

    //查询登录用户的权限列表
    List<String> queryLoginUrl(Integer userId) throws SQLException;

    //查询用户数据
    List<User> queryUser(Integer page, Integer pageSize, Integer userId,String userName) throws SQLException;

    //查询数据总条数
    long queryUserNum(Integer userId,String UserName) throws SQLException;

    //增加用户数据
    int addUser(User user) throws SQLException;

    //根据用户编号查询用户数据
    User queryUserId(Integer userId) throws SQLException;

    //根据用户编号和版本编号查询用户数据
    User queryByMidAndVid(Integer userId, Integer versionId) throws SQLException;

    //修改用户数据
    int editUser(User user) throws SQLException, ParseException;

    //删除用户数据
    int deleteUser(Connection conn, Integer userId) throws SQLException;

    //根据用户编号查询用户权限编号列表
    List<Integer> queryMenuIdList(Integer userId) throws SQLException;

    // 删除用户和其菜单数据
    int deleteUserMenu(Connection conn, Integer userId) throws SQLException;

    //保存最新的用户权限数据
    int addUserMenu(Object[][] userMenu, Connection conn) throws SQLException;
}
