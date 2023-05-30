package com.javasm.finance.service.impl;

import com.javasm.finance.dao.CheckDao;
import com.javasm.finance.dao.impl.CheckDaoImpl;
import com.javasm.finance.entity.Product;
import com.javasm.finance.service.CheckService;

import java.sql.SQLException;
import java.util.List;

public class CheckServiceImpl implements CheckService {
   CheckDao checkDao = new CheckDaoImpl();
    @Override
    public List<Product> queryCheckProd(Integer page, Integer pageSize, String productName, Integer auditStatus, Integer checkUser) {
        List<Product> checkProductList = null;
        try {
            checkProductList = checkDao.queryCheckProd(page,pageSize,productName,auditStatus,checkUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return checkProductList;
    }

    @Override
    public long totalNum(String productName, Integer auditStatus, Integer checkUser) {
        long totalNum = 0;
        try {
            totalNum = checkDao.totalNum(productName,auditStatus,checkUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalNum;
    }

    @Override
    public Product queryById(Integer productId) {
        Product productById = null;
        try {
            productById = checkDao.queryById(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productById;
    }

    @Override
    public int editAuditStatusApprove(Integer productId) {
        int i= 0;
        try {
            i = checkDao.editAuditStatusApprove(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public int editAuditStatusReject(Integer productId) {
        int i = 0;
        try {
            i = checkDao.editAuditStatusReject(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }
}
