package com.javasm.finance.dao;

import com.javasm.finance.entity.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface ProductCategoryDao {

    //查询产品系列数据
    List<ProductCategory> queryProductCategory(Integer page, Integer pageSize, String name) throws SQLException;

    //查询数据总条数
    long queryProductCategoryNum(String name) throws SQLException;

    //添加数据
    int addProductCategory(ProductCategory pc) throws SQLException;

    //根据系列编号显示修改数据
    ProductCategory queryProductCategoryId(Integer productCategoryId) throws SQLException;

    //根据系列编号和版本号来判断是否被修改
    ProductCategory queryByMidAndVid(Integer productCategoryId, Integer versionId) throws SQLException;

    //修改数据
    int editProductCategory(ProductCategory pc) throws SQLException;
}
