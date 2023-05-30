package com.javasm.finance.dao.impl;


import com.javasm.finance.dao.UserDao;
import com.javasm.finance.entity.User;
import com.javasm.finance.listener.DBListener;
import com.javasm.finance.util.DButils2;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    QueryRunner run = new QueryRunner(DButils2.getDruidDataSource());

    @Override
    public User queryLoginUser(String userName, String userPwd) throws SQLException {
        String sql = "select " +
                "user_id,user_name,user_pwd,role_id,reg_time,login_time,is_valid,head_img,version_id " +
                "from fin_admin_user " +
                "where user_name=? " +
                "and user_pwd=?";
        BeanHandler<User> beanHandler = new BeanHandler<>(User.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        User user = run.query(sql,
                beanHandler,
                userName,
                userPwd);
        return user;
    }

    @Override
    public List<String> queryLoginUrl(Integer userId) throws SQLException {
        String sql = "select am.menu_url " +
                "from fin_admin_menu am,fin_user_menu um " +
                "where am.menu_id = um.menu_id " +
                "and am.parent_id !=0 " +
                "and um.user_id =?";
        // 把某个字段的值，封装到一个list集合中
        ColumnListHandler<String> columnListHandler = new ColumnListHandler<>("menu_url");
        List<String> loginUrlList = run.query(sql,
                columnListHandler,
                userId);
        return loginUrlList;
    }

    @Override
    public List<User> queryUser(Integer page, Integer pageSize, Integer userId, String userName) throws SQLException {
        String sql = "select " +
                "user_id,user_name,role_id,reg_time,login_time,is_valid,head_img " +
                "from " +
                "fin_admin_user ";
        boolean isWhere = true;
        List paramList = new ArrayList();
        if (userId != null && !"".equals(userId)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "user_id=? ";
            paramList.add(userId);
        }
        if (userName != null && !"".equals(userName)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "user_name like ? ";
            paramList.add("%" + userName + "%");
        }
        sql += "limit " + (page - 1) * pageSize + "," + pageSize;
        BeanListHandler<User> beanListHandler = new BeanListHandler<>(User.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        List<User> userList = run.query(sql, beanListHandler, paramList.toArray());
        return userList;
    }

    @Override
    public long queryUserNum(Integer userId, String UserName) throws SQLException {
        String sql = "select count(1) total from fin_admin_user ";
        boolean isWhere = true;
        List paramList = new ArrayList();
        if (userId != null && !"".equals(userId)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "user_id=? ";
            paramList.add(userId);
        }
        if (UserName != null && !"".equals(UserName)) {
            if (isWhere) {
                sql += "where ";
                isWhere = false;
            } else {
                sql += "and ";
            }
            sql += "user_name like ? ";
            paramList.add("%" + UserName + "%");
        }
        long total = run.query(sql, new ScalarHandler<>(), paramList.toArray());
        return total;
    }

    @Override
    public int addUser(User user) throws SQLException {
        String sql = "insert into " +
                "fin_admin_user " +
                "(user_name,user_pwd,role_id,reg_time,is_valid,head_img,version_id) " +
                "values (?,'1234',?,?,?,?,100)";
        int row = run.update(sql, user.getUserName(), user.getRoleId(), user.getRegTime(),
                user.getIsValid(), user.getHeadImg());
        return row;
    }

    @Override
    public User queryUserId(Integer userId) throws SQLException {
        String sql = "select " +
                "user_id,user_name,user_pwd,role_id,reg_time,is_valid,head_img,version_id " +
                "from fin_admin_user " +
                "where user_id = ?";
        BeanHandler<User> beanHandler = new BeanHandler<>(User.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        User user = run.query(sql, beanHandler, userId);
        return user;
    }

    @Override
    public User queryByMidAndVid(Integer userId, Integer versionId) throws SQLException {
        String sql = "select user_id,user_name,user_pwd,role_id,reg_time,is_valid,head_img ,version_id " +
                "from fin_admin_user where user_id=? " +
                "and version_id=?";
        BeanHandler<User> beanHandler = new BeanHandler<>(User.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        User user = run.query(sql, beanHandler, userId, versionId);
        return user;
    }

    @Override
    public int editUser(User user) throws SQLException{
        String sql = "update " +
                "fin_admin_user " +
                "set " +
                "user_name = ?,role_id=?,reg_time=?,is_valid=?,head_img=?,version_id=?+1 " +
                "where " +
                "user_id=?";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = sdf.parse(user.getRegTime());
//        System.out.println(date);
        int row = run.update(sql, user.getUserName(), user.getRoleId(), user.getRegTime(), user.getIsValid(), user.getHeadImg(), user.getVersionId(), user.getUserId());
        return row;
    }

    @Override
    public int deleteUser(Connection conn, Integer userId) throws SQLException {
        String sql = "delete from fin_admin_user where user_id = ?";
        int row = run.update(conn, sql, userId);
        return row;
    }

    @Override
    public List<Integer> queryMenuIdList(Integer userId) throws SQLException {
        String sql = "select * from fin_user_menu where user_id=?";
        ColumnListHandler<Integer> columnListHandler = new ColumnListHandler<>("menu_id");
        List<Integer> userList = run.query(sql,columnListHandler, userId);
        return userList;
    }

    @Override
    public int deleteUserMenu(Connection conn, Integer userId) throws SQLException {
        String sql = "delete from fin_user_menu where user_id=?";
        int row = run.update(conn, sql, userId);
        return row;
    }

    @Override
    public int addUserMenu(Object[][] userMenu, Connection conn) throws SQLException {
        String sql = "insert into fin_user_menu(user_id,menu_id) values (?,?)";
        int row = run.batch(conn,sql,userMenu).length;
        return row;
    }
}
