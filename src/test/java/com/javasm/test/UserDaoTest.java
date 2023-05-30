package com.javasm.test;

import com.javasm.finance.dao.UserDao;
import com.javasm.finance.dao.impl.UserDaoImpl;
import com.javasm.finance.entity.User;
import com.javasm.finance.service.UserService;
import com.javasm.finance.service.impl.UserServiceImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

public class UserDaoTest {
    @Test
    public void test(){
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl();
        try {
            System.out.println(userService.queryMenuIdStr(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
