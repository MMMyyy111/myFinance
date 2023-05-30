package com.javasm.finance.dao;

import com.javasm.finance.entity.Menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MenuDao {
    // 根据用户编号和菜单等级查询菜单数据
    List<Menu> queryMenuList(Integer userId,Integer level) throws SQLException;

    //查询菜单数据
    List<Menu> queryMenu(Integer page, Integer pageSize, String menuName, Integer parentId) throws SQLException;

    //查询数据总条数
    long queryMenuNum(String menuName, Integer parentId) throws SQLException;

    // 查询所有的一级菜单数据
    List<Menu> queryOneMenu() throws SQLException;

    //根据菜单编号查询菜单数据
    Menu queryMenuId(Integer menuId) throws SQLException;

    //增加菜单数据
    int addMenu(Menu menu) throws SQLException;

    //根据菜单编号和版本编号查询菜单数据
    Menu queryByMidAndVid(Integer menuId, Integer versionId) throws SQLException;

    //修改菜单数据
    int editMenu(Menu menu) throws SQLException;

    //删除菜单数据
    int deleteMenu(Connection conn,Integer menuId) throws SQLException;

    //根据上级编号查询下级菜单数据
    List<Menu> queryByPid(Integer parentId) throws SQLException;

    // 删除下级菜单数据
    int deleteByPid(Connection conn, Integer parentId) throws SQLException;

    //根据菜单等级查询菜单数据
    List<Menu> queryByLevel(Integer level) throws SQLException;
}
