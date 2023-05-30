package com.javasm.finance.service;

import com.javasm.finance.entity.Menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MenuService {
    // 根据用户编号和菜单等级查询菜单数据
    List<Menu> queryMenuList(Integer userId);

    //查询菜单数据
    List<Menu> queryMenu(Integer page, Integer pageSize, String menuName, Integer parentId);

    //查询数据总条数
    long queryMenuNum(String menuName, Integer parentId);

    // 查询所有的一级菜单数据
    List<Menu> queryOneMenu();

    //根据菜单编号查询菜单数据
    Menu queryMenuId(Integer menuId);

    //增加菜单数据
    int addMenu(Menu menu);

    //根据菜单编号和版本编号查询菜单数据
    Menu queryByMidAndVid(Integer menuId, Integer versionId);

    //修改菜单数据
    int editMenu(Menu menu);

    //删除菜单数据
    boolean deleteMenu(Integer menuId);

    //根据菜单等级查询菜单数据
    List<Menu> queryByLevel();

}
