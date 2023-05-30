package com.javasm.finance.util;

import java.sql.*;
import java.util.Properties;

/**
 * @author: ShangMa
 * @className: DbUtils
 * @description: 工具类-处理数据库连接
 * @date: 2023/1/10 10:03
 * @since: 11
 */
public class DbUtils {
    static String driver;
    static String url;
    static String user;
    static String pwd;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DbUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.username");
            pwd = properties.getProperty("jdbc.password");
            //把对应的类 加载到虚拟机中
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConn() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接
     *
     * @param connection        连接对象
     * @param preparedStatement 语句对象
     * @param resultSet         结果集对象
     */
    public static void getClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) connection.close();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
