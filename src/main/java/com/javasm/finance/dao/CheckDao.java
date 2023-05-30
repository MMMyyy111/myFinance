package com.javasm.finance.dao;

import com.javasm.finance.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface CheckDao {
    List<Product> queryCheckProd(Integer page,Integer pageSize,String productName, Integer auditStatus,Integer checkUser) throws SQLException;
    long totalNum(String productName, Integer auditStatus,Integer checkUser) throws SQLException;
    Product queryById(Integer productId) throws SQLException;
    int editAuditStatusApprove(Integer productId) throws SQLException;
    int editAuditStatusReject(Integer productId) throws SQLException;
}
