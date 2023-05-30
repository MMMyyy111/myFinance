package com.javasm.finance.dao.impl;

import com.javasm.finance.dao.MenuDao;
import com.javasm.finance.entity.Menu;
import com.javasm.finance.util.DbUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {
    QueryRunner run = new QueryRunner();

    @Override
    public List<Menu> queryMenuList(Integer userId, Integer level) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select " +
                "am.menu_id,am.menu_name,am.parent_id,am.menu_url,am.glyphicon,am.version_id " +
                "from " +
                "fin_user_menu um,fin_admin_menu am " +
                "where " +
                "um.menu_id = am.menu_id " +
                "and " +
                "um.user_id=? ";
        if (level == 1) {
            // 一级菜单数据
            sql += "and am.parent_id=0";
        } else if (level == 2) {
            // 二级菜单数据
            sql += "and am.parent_id!=0";
        }
        BeanListHandler<Menu> listHandler = new BeanListHandler<>(Menu.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Menu> menuList = run.query(conn, sql, listHandler, userId);
        DbUtils.getClose(conn, null, null);
        return menuList;
    }

    @Override
    public List<Menu> queryMenu(Integer page, Integer pageSize, String menuName, Integer parentId) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select " +
                "am.menu_id,am.menu_name,am.parent_id," +
                "ifnull(am2.menu_name,'超级菜单') parent_name,am.menu_url,am.glyphicon,am.version_id " +
                "from " +
                "fin_admin_menu am " +
                "left " +
                "join fin_admin_menu am2 " +
                "on " +
                "am.parent_id=am2.menu_id ";
        boolean isWhere = true;
        List paramList = new ArrayList();
        if (menuName != null && !"".equals(menuName)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "am.menu_name like ? ";
            paramList.add("%" + menuName + "%");
        }
        if (parentId != null && !"".equals(parentId)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "am.parent_id=? ";
            paramList.add(parentId);
        }
        sql += "limit " + (page - 1) * pageSize + "," + pageSize;
        BeanListHandler<Menu> listHandler = new BeanListHandler<>(Menu.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Menu> menuList = run.query(conn, sql, listHandler, paramList.toArray());
        DbUtils.getClose(conn, null, null);
        return menuList;
    }

    @Override
    public long queryMenuNum(String menuName, Integer parentId) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select count(1) total from fin_admin_menu am ";
        boolean isWhere = true;
        List paramList = new ArrayList();
        if (menuName != null && !"".equals(menuName)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "am.menu_name like ? ";
            paramList.add("%" + menuName + "%");
        }
        if (parentId != null && !"".equals(parentId)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "am.parent_id=? ";
            paramList.add(parentId);
        }
        // ScalarHandler：查询单数据
        long total = run.query(conn, sql, new ScalarHandler<>(), paramList.toArray());
        DbUtils.getClose(conn, null, null);
        return total;
    }

    @Override
    public List<Menu> queryOneMenu() throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select am.menu_id,am.menu_name,am.parent_id,am.menu_url,am.glyphicon,am.version_id " +
                "from fin_admin_menu am " +
                "where am.parent_id=0";
        BeanListHandler<Menu> beanListHandler = new BeanListHandler<>(Menu.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Menu> oneMenuList = run.query(conn, sql, beanListHandler);
        DbUtils.getClose(conn, null, null);
        return oneMenuList;
    }

    public Menu queryMenuId(Integer menuId) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select menu_id,menu_name,parent_id,menu_url,glyphicon,version_id " +
                "from fin_admin_menu " +
                "where menu_id=?";
        BeanHandler<Menu> beanHandler = new BeanHandler<>(Menu.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        Menu menu = run.query(conn, sql, beanHandler, menuId);
        DbUtils.getClose(conn, null, null);
        return menu;
    }

    @Override
    public int addMenu(Menu menu) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "insert into fin_admin_menu(menu_id,menu_name,parent_id,menu_url,glyphicon,version_id)  " +
                "values (?,?,?,?,?,100)";
        int row = run.update(conn, sql, menu.getMenuId(), menu.getMenuName(), menu.getParentId(), menu.getMenuUrl(), menu.getGlyphicon());
        DbUtils.getClose(conn, null, null);
        return row;
    }

    @Override
    public Menu queryByMidAndVid(Integer menuId, Integer versionId) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select menu_id,menu_name,parent_id,menu_url,glyphicon,version_id " +
                "from fin_admin_menu where menu_id=? " +
                "and version_id=?";
        BeanHandler<Menu> beanHandler = new BeanHandler<>(Menu.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        Menu menu = run.query(conn, sql, beanHandler, menuId, versionId);
        DbUtils.getClose(conn, null, null);
        return menu;
    }

    @Override
    public int editMenu(Menu menu) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "update " +
                "fin_admin_menu " +
                "set " +
                "menu_name=?,parent_id=?,menu_url=?,glyphicon=?,version_id=?+1 " +
                "where " +
                "menu_id=?";
        int row = run.update(conn, sql,  menu.getMenuName(), menu.getParentId(), menu.getMenuUrl(),
                menu.getGlyphicon(), menu.getVersionId(), menu.getMenuId());
        DbUtils.getClose(conn, null, null);
        return row;
    }

    @Override
    public int deleteMenu(Connection conn,Integer menuId) throws SQLException {
        String sql = "delete from fin_admin_menu where menu_id=?";
        int row = run.update(conn, sql, menuId);
        return row;
    }

    @Override
    public List<Menu> queryByPid(Integer parentId) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select menu_id,menu_name,parent_id,menu_url,glyphicon,version_id " +
                "from fin_admin_menu where parent_id=?";
        BeanListHandler<Menu> beanListHandler = new BeanListHandler<>(Menu.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Menu> menuList = run.query(conn, sql,beanListHandler, parentId);
        DbUtils.getClose(conn, null, null);
        return menuList;
    }

    @Override
    public int deleteByPid(Connection conn, Integer parentId) throws SQLException {
        String sql = "delete from fin_admin_menu where parent_id=?";
        int row = run.update(conn, sql, parentId);
        return row;
    }

    @Override
    public List<Menu> queryByLevel(Integer level) throws SQLException {
        Connection conn = DbUtils.getConn();
        String sql = "select " +
                "menu_id,menu_name,parent_id,menu_url,glyphicon,version_id " +
                "from " +
                "fin_admin_menu am ";
        if (level == 1) {
            // 一级菜单数据
            sql += "where am.parent_id=0";
        } else if (level == 2) {
            // 二级菜单数据
            sql += "where am.parent_id!=0";
        }
        BeanListHandler<Menu> listHandler = new BeanListHandler<>(Menu.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Menu> menuList = run.query(conn, sql, listHandler);
        DbUtils.getClose(conn, null, null);
        return menuList;
    }

}
