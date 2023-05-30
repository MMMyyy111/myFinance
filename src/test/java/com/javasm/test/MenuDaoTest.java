package com.javasm.test;

import com.javasm.finance.dao.MenuDao;
import com.javasm.finance.dao.impl.MenuDaoImpl;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.service.MenuService;
import com.javasm.finance.service.impl.MenuServiceImpl;
import org.junit.Test;

import java.sql.SQLException;

public class MenuDaoTest {
    MenuDao menuDao = new MenuDaoImpl();
    MenuService menuService = new MenuServiceImpl();

    @Test
    public void test() throws SQLException {
//        System.out.println(menuDao.queryMenuId(1));
         Menu menu = new Menu(666, "11", 11, "gg", "11", "user", null, null);
        System.out.println(menuDao.queryByLevel(1));
    }
}
