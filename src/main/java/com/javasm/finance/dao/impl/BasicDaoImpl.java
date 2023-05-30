package com.javasm.finance.dao.impl;

import com.javasm.finance.dao.BasicDao;
import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductCategory;
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

public class BasicDaoImpl implements BasicDao{
    QueryRunner queryRunner = new QueryRunner(DButils2.getDruidDataSource());
    @Override
    public List<Product> query(String userName,String productName,Integer productCategoryId,Integer auditStatus ,Integer page,Integer pageSize) throws SQLException {
        ArrayList paramList = new ArrayList<>();
        String sql = "SELECT product_name,product_category_name," +
                "product_sec_category,product_company_name,op_date," +
                "fund_currency,audit_status,version_id FROM fin_product_basic WHERE create_user=? ";
        paramList.add(userName);
        if(productName!=null&&!"".equals(productName)){
            sql+= "and product_name like ? ";
            paramList.add("%"+productName+"%");
        }
        if(productCategoryId!=null&&!"".equals(productCategoryId)){
            sql+= " and product_category_id = ? ";
            paramList.add(productCategoryId);
        }
        if(auditStatus!=null&&!"".equals(auditStatus)){
            sql+= " and audit_status = ? ";
            paramList.add(auditStatus);
        }

        sql+="limit "+(page-1)*pageSize+","+pageSize;
        BeanListHandler<Product> handler = new BeanListHandler<>(Product.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        List<Product> productList = queryRunner.query(sql, handler, paramList.toArray());
        return productList;
    }

    @Override
    public long queryNum(String userName,String productName,Integer productCategoryId,Integer auditStatus) throws SQLException {
        ArrayList paramList = new ArrayList<>();
        String sql="select count(1) from fin_product_basic WHERE create_user=?";
        paramList.add(userName);
        if(productName!=null&&!"".equals(productName)){
            sql+= "and product_name like ? ";
            paramList.add("%"+productName+"%");
        }
        if(productCategoryId!=null&&!"".equals(productCategoryId)){
            sql+= " and product_category_id = ? ";
            paramList.add(productCategoryId);
        }
        if(auditStatus!=null&&!"".equals(auditStatus)){
            sql+= " and audit_status = ? ";
            paramList.add(auditStatus);
        }
        long total = queryRunner.query(sql, new ScalarHandler<>(), paramList.toArray());
        return total;
    }

    @Override
    public List<ProductCategory> queryCategory() throws SQLException {
        String sql="select product_category_id,product_category_name from fin_product_category";
        BeanListHandler<ProductCategory> handler = new BeanListHandler<>(ProductCategory.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        List<ProductCategory> categoryList = queryRunner.query(sql, handler);
        return categoryList;
    }
}
