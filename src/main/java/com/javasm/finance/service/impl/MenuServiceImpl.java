package com.javasm.finance.service.impl;

import com.javasm.finance.dao.MenuDao;
import com.javasm.finance.dao.impl.MenuDaoImpl;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.service.MenuService;
import com.javasm.finance.util.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    MenuDao menuDao = new MenuDaoImpl();

    @Override
    public List<Menu> queryMenuList(Integer userId) {
        // 一级菜单
        List<Menu> menuList = null;
        // 二级菜单
        List<Menu> subMenuList = null;
        try {
            menuList = menuDao.queryMenuList(userId,1);
            subMenuList = menuDao.queryMenuList(userId,2);
            // 将二级菜单数据放到一级菜单数据中
            for (Menu menu : menuList) {
                for (Menu subMenu : subMenuList) {
                    if (menu.getMenuId().equals(subMenu.getParentId())) {
                        if (menu.getSubMenu() == null) {
                            menu.setSubMenu(new ArrayList<>());
                        }
                        menu.getSubMenu().add(subMenu);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menuList;
    }

    @Override
    public List<Menu> queryMenu(Integer page, Integer pageSize, String menuName, Integer parentId) {
        List<Menu> menuList = null;
        try {
            menuList = menuDao.queryMenu(page, pageSize, menuName, parentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menuList;
    }

    @Override
    public long queryMenuNum(String menuName, Integer parentId) {
        long total = 0;
        try {
            total = menuDao.queryMenuNum(menuName, parentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    @Override
    public List<Menu> queryOneMenu() {
        List<Menu> oneMenuList =null;
        try{
            oneMenuList = menuDao.queryOneMenu();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return oneMenuList;
    }

    @Override
    public Menu queryMenuId(Integer menuId){
        Menu menu = null;
        try{
             menu = menuDao.queryMenuId(menuId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menu;
    }

    @Override
    public int addMenu(Menu menu) {
        int row = 0;
        try{
            row = menuDao.addMenu(menu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    @Override
    public Menu queryByMidAndVid(Integer menuId, Integer versionId) {
        Menu menu = null;
        try{
            menu = menuDao.queryByMidAndVid(menuId,versionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menu;
    }

    @Override
    public int editMenu(Menu menu) {
        int row = 0;
        try {
            row = menuDao.editMenu(menu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    @Override
    public boolean deleteMenu(Integer menuId) {
        boolean isSuccess = false;
        Connection conn = DbUtils.getConn();
        try {
            // 关闭事务自动提交
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            List<Menu> menuList = menuDao.queryByPid(menuId);
            if (menuList != null && menuList.size()>0) {
                // 存在下级菜单数据
                menuDao.deleteByPid(conn, menuId);
            }
            menuDao.deleteMenu(conn,menuId);
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
    public List<Menu> queryByLevel() {
        // 一级菜单
        List<Menu> menuList = null;
        // 二级菜单
        List<Menu> subMenuList = null;
        try {
            menuList = menuDao.queryByLevel(1);
            subMenuList = menuDao.queryByLevel(2);
            // 将二级菜单数据放到一级菜单数据中
            for (Menu menu : menuList) {
                for (Menu subMenu : subMenuList) {
                    if (menu.getMenuId().equals(subMenu.getParentId())) {
                        if (menu.getSubMenu() == null) {
                            menu.setSubMenu(new ArrayList<>());
                        }
                        menu.getSubMenu().add(subMenu);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menuList;
    }

}
