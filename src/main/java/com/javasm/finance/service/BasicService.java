package com.javasm.finance.service;

import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductCategory;

import java.util.List;

public interface BasicService {
    List<Product> query(String userName,String productName,Integer productCategoryId,Integer auditStatus,Integer page, Integer pageSize);
    long queryNum(String userName,String productName,Integer productCategoryId,Integer auditStatus);
    List<ProductCategory> queryCategory();
}
