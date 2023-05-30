package com.javasm.finance.dao;

import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface BasicDao {
    List<Product> query(String userName ,String productName,Integer productCategoryId,Integer auditStatus,Integer page,Integer pageSize) throws SQLException;
    long queryNum(String userName,String productName,Integer productCategoryId,Integer auditStatus) throws SQLException;
    List<ProductCategory> queryCategory() throws SQLException;
}
