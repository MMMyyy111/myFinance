package com.javasm.finance.service;

import com.javasm.finance.entity.Product;

import java.util.List;

public interface CheckService {
    List<Product> queryCheckProd(Integer page, Integer pageSize, String productName, Integer auditStatus, Integer checkUser);

    long totalNum(String productName, Integer auditStatus, Integer checkUser);
    Product queryById(Integer productId);
    int editAuditStatusApprove(Integer productId);
    int editAuditStatusReject(Integer productId);
}
