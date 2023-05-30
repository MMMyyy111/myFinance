package com.javasm.finance.service;


import com.javasm.finance.entity.User;

import java.util.List;

public interface UserService {

    //根据用户名和密码查询登录的用户数据
    User queryLoginUser(String userName, String userPwd);

    //查询登录用户的权限列表
    List<String> queryLoginUrl(Integer userId);

    //查询用户数据
    List<User> queryUser(Integer page, Integer pageSize, Integer userId, String userName);

    //查询数据总条数
    long queryUserNum(Integer userId, String UserName);

    //增加用户数据
    int addUser(User user);

    //根据用户编号查询用户数据
    User queryUserId(Integer userId);

    //根据用户编号和版本编号查询用户数据
    User queryByMidAndVid(Integer userId, Integer versionId);

    //修改用户数据
    int editUser(User user);

    //删除用户数据
    boolean deleteUser(Integer userId);

    //根据用户编号查询用户权限编号列表
    String queryMenuIdStr(Integer userId);

    //保存最新用户权限数据
    boolean isSuccess(Integer userId, Object[][] userMenu);
}
