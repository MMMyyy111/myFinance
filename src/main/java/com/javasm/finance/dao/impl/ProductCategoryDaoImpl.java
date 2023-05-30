package com.javasm.finance.dao.impl;

import com.javasm.finance.dao.ProductCategoryDao;
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

public class ProductCategoryDaoImpl implements ProductCategoryDao {
    QueryRunner run = new QueryRunner(DButils2.getDruidDataSource());

    @Override
    public List<ProductCategory> queryProductCategory(Integer page, Integer pageSize, String name) throws SQLException {
        String sql = "select " +
                "pc.product_category_id,pc.product_category_name,pc.product_en_category_name,ac.company_name " +
                "from fin_product_category pc " +
                "left join fin_am_company ac " +
                "on pc.product_am_company = ac.company_id ";
        List paramList = new ArrayList();
        if (name != null && !"".equals(name)) {
            if (name.matches("^[a-zA-Z]*")) {
                sql += "where pc.product_en_category_name like ? ";
                paramList.add("%" + name + "%");
            }else {
                sql += "where pc.product_category_name like ? ";
                paramList.add("%" + name + "%");
            }
        }
        sql += "limit " + (page - 1) * pageSize + "," + pageSize;
        BeanListHandler<ProductCategory> beanListHandler = new BeanListHandler<>(ProductCategory.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        List<ProductCategory> productCategoryList = run.query(sql, beanListHandler, paramList.toArray());
        return productCategoryList;
    }

    @Override
    public long queryProductCategoryNum(String name) throws SQLException {
        String sql = "select count(1) total from fin_product_category pc ";
        List paramList = new ArrayList();
        if (name != null && !"".equals(name)) {
            if (name.matches("^[a-zA-Z]*")) {
                sql += "where pc.product_en_category_name like ? ";
                paramList.add("%" + name + "%");
            }else {
                sql += "where pc.product_category_name like ? ";
                paramList.add("%" + name + "%");
            }
        }
        long total = run.query(sql, new ScalarHandler<>(), paramList.toArray());
        return total;
    }

    @Override
    public int addProductCategory(ProductCategory pc) throws SQLException {
        String sql = "insert into " +
                "fin_product_category " +
                "(product_channel,product_type,product_category_name,product_en_category_name,version_id) " +
                "values (?,?,?,?,100)";
        final int row = run.update(sql, pc.getProductChannel(),
                pc.getProductType(), pc.getProductCategoryName(), pc.getProductEnCategoryName());
        return row;
    }

    @Override
    public ProductCategory queryProductCategoryId(Integer productCategoryId) throws SQLException {
        String sql = "select * " +
//                "product_channel,product_type,product_category_name,product_en_category_name,version_id " +
                "from fin_product_category " +
                "where product_category_id = ?";
         BeanHandler<ProductCategory> beanHandler = new BeanHandler<>(ProductCategory.class,
                 new BasicRowProcessor(new GenerousBeanProcessor()));
        ProductCategory ProductCategory= run.query(sql, beanHandler, productCategoryId);
        return ProductCategory;
    }

    @Override
    public ProductCategory queryByMidAndVid(Integer productCategoryId, Integer versionId) throws SQLException {
        String sql = "select * " +
                "from fin_product_category " +
                "where product_category_id = ? " +
                "and version_id = ?";
        BeanHandler<ProductCategory> beanHandler = new BeanHandler<>(ProductCategory.class,
                new BasicRowProcessor(new GenerousBeanProcessor()));
        ProductCategory productCategory = run.query(sql, beanHandler, productCategoryId, versionId);
        return productCategory;
    }

    @Override
    public int editProductCategory(ProductCategory pc) throws SQLException {
        String sql = "update " +
                "fin_product_category " +
                "set product_channel=?,product_type=?," +
                "product_category_name=?,product_en_category_name=?,version_id=?+1 " +
                "where product_category_id = ?";
        int row = run.update(sql, pc.getProductChannel(), pc.getProductType(), pc.getProductCategoryName(),
                pc.getProductEnCategoryName(), pc.getVersionId(), pc.getProductCategoryId());
        return row;
    }
}
