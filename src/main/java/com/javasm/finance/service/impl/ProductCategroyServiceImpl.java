package com.javasm.finance.service.impl;

import com.javasm.finance.dao.ProductCategoryDao;
import com.javasm.finance.dao.impl.ProductCategoryDaoImpl;
import com.javasm.finance.entity.ProductCategory;
import com.javasm.finance.service.ProductCategroyService;

import java.sql.SQLException;
import java.util.List;

public class ProductCategroyServiceImpl implements ProductCategroyService {
    ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();

    @Override
    public List<ProductCategory> queryProductCategory(Integer page, Integer pageSize, String name) {
        List<ProductCategory> productCategoryList = null;
        try {
            productCategoryList = productCategoryDao.queryProductCategory(page,pageSize,name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productCategoryList;
    }

    @Override
    public long queryProductCategoryNum(String name) {
        long total = 0;
        try{
            total = productCategoryDao.queryProductCategoryNum(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    @Override
    public int addProductCategory(ProductCategory pc) {
        int row = 0;
        try{
            row = productCategoryDao.addProductCategory(pc);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    @Override
    public ProductCategory queryProductCategoryId(Integer productCategoryId) {
        ProductCategory productCategory = null;
        try{
            productCategory = productCategoryDao.queryProductCategoryId(productCategoryId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productCategory;
    }

    @Override
    public ProductCategory queryByMidAndVid(Integer productCategoryId, Integer versionId) {
        ProductCategory productCategory = null;
        try{
            productCategory = productCategoryDao.queryByMidAndVid(productCategoryId,versionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productCategory;
    }

    @Override
    public int editProductCategory(ProductCategory pc) {
        int row = 0;
        try{
            row = productCategoryDao.editProductCategory(pc);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }
}
