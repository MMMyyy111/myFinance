package com.javasm.test;

import com.javasm.finance.dao.CheckDao;
import com.javasm.finance.dao.impl.CheckDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class CheckDaoTest {

    CheckDao checkDao = new CheckDaoImpl();
    @Test
    public void test(){
        try {
            System.out.println(checkDao.queryById(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
