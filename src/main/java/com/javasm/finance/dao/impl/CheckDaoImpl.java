package com.javasm.finance.dao.impl;

import com.javasm.finance.dao.CheckDao;
import com.javasm.finance.entity.Product;
import com.javasm.finance.util.DButils2;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckDaoImpl implements CheckDao {
    QueryRunner run = new QueryRunner(DButils2.getDruidDataSource());
    @Override
    public List<Product> queryCheckProd(Integer page,Integer pageSize,String productName, Integer auditStatus,Integer checkUser) throws SQLException {
        BeanListHandler<Product> listHandler = new BeanListHandler<>(Product.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        String sql = "SELECT product_id, product_name,product_category_name," +
               "product_sec_category,product_company_name,create_user,create_time,last_update_time,annualized_rate," +
               "fund_currency,op_date,subscription_cycle,relative_manage_cost,subscription_rate,start_amount,charge_mode,redeming_cycle," +
               "redeming_start_amount,redeming_rate,redeming_lockUp," +
               "check_user,version_id FROM fin_product_basic WHERE check_user=? ";
        boolean isExist = true;
        List paras = new ArrayList();
        paras.add(checkUser);
        if(productName!=null && !"".equals(productName)){
                sql += "AND product_name LIKE ? ";
                paras.add("%"+productName+"%");
        }
        if(auditStatus!=null && !"".equals(auditStatus)){
            sql += "AND audit_status = ? ";
            paras.add(auditStatus);
        }
        sql += "limit "+(page-1)*pageSize+","+pageSize;
        List<Product> checkProductList = run.query(sql, listHandler, paras.toArray());
        return checkProductList;
    }

    @Override
    public long totalNum(String productName, Integer auditStatus, Integer checkUser) throws SQLException {
        String sql = "SELECT COUNT(1) FROM fin_product_basic WHERE check_user=? ";
        List paras = new ArrayList();
        paras.add(checkUser);
        if(productName!=null && !"".equals(productName)){
            sql += "AND product_name LIKE ? ";
            paras.add("%"+productName+"%");
        }
        if(auditStatus!=null && !"".equals(auditStatus)){
            sql += "AND audit_status=? ";
            paras.add(auditStatus);
        }
        long totalNum = run.query(sql, new ScalarHandler<>(), paras.toArray());
        return totalNum;
    }

    @Override
    public Product queryById(Integer productId) throws SQLException {
        BeanHandler<Product> beanHandler = new BeanHandler<>(Product.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        String sql = "SELECT product_id, product_name,product_category_name," +
                "product_sec_category,product_company_name,create_user,create_time,last_update_time,annualized_rate," +
                "fund_currency,op_date,subscription_cycle,relative_manage_cost,subscription_rate,start_amount,charge_mode,redeming_cycle," +
                "redeming_start_amount,redeming_rate,redeming_lockUp," +
                "audit_status,check_user,au.user_name check_user_name,pb.version_id FROM fin_product_basic pb " +
                "LEFT JOIN fin_admin_user au " +
                "ON pb.check_user=au.user_id WHERE product_id=? ";
        Product productById = run.query(sql, beanHandler, productId);
        return productById;
    }

    @Override
    public int editAuditStatusApprove(Integer productId) throws SQLException {
        String sql = "UPDATE fin_product_basic SET audit_status=2 WHERE product_id=?";
        int i = run.update(sql, productId);
        return i;
    }

    @Override
    public int editAuditStatusReject(Integer productId) throws SQLException {
        String sql = "UPDATE fin_product_basic SET audit_status=0,version_id=?+1 WHERE product_id=?";
        int i = run.update(sql, productId);
        return i;
    }
}
