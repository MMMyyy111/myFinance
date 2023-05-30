package com.javasm.finance.service.impl;

import com.javasm.finance.dao.impl.BasicDaoImpl;
import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductCategory;
import com.javasm.finance.service.BasicService;

import java.sql.SQLException;
import java.util.List;

public class BasicServiceImpl implements BasicService {
    BasicDaoImpl basicDao = new BasicDaoImpl();
    @Override
    public List<Product> query(String userName,String productName,Integer productCategoryId,Integer auditStatus, Integer page, Integer pageSize) {
        List<Product> productList = null;
        try {
            productList = basicDao.query(userName, productName, productCategoryId, auditStatus, page, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productList;
    }

    @Override
    public long queryNum(String userName,String productName,Integer productCategoryId,Integer auditStatus) {
        long total = 0;
        try {
            total = basicDao.queryNum(userName,productName,productCategoryId,auditStatus);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    @Override
    public List<ProductCategory> queryCategory() {
        List<ProductCategory> productCategoryList = null;
        try {
            productCategoryList = basicDao.queryCategory();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productCategoryList;
    }
}
