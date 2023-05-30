package com.javasm.finance.listener;


import com.alibaba.druid.pool.DruidDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class DBListener implements ServletContextListener {
    //创建数据库连接池对象
    static DruidDataSource ds = new DruidDataSource();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取ServletContext对象
         ServletContext context = sce.getServletContext();
        //初始化数据库连接池资源
        ds.setDriverClassName(context.getInitParameter("jdbc.driver"));
        ds.setUrl(context.getInitParameter("jdbc.url"));
        ds.setUsername(context.getInitParameter("jdbc.username"));
        ds.setPassword(context.getInitParameter("jdbc.password"));
        try {
            ds.init();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DruidDataSource getDruidDataSource(){
        return ds;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //关闭数据库连接池
        ds.close();
    }
}
