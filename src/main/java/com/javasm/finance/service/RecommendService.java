package com.javasm.finance.service;

import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductRecommend;

import java.util.List;

public interface RecommendService {
    List<ProductRecommend> queryRecommend(Integer page,Integer pageSize,String productName);
    long recommendTotal(String productName);
    List<Product> queryNotRecom();
    ProductRecommend queryRecomById(Integer productId);
    int addRecommend(ProductRecommend productRecommend);
    int editRecommend(ProductRecommend productRecommend);
    ProductRecommend queryRecomByPidVid(Integer productId,Integer versionId);
    List<Product> querySeriesProd(Integer productId);
    List<Integer> queryLinkedId(Integer productId);
    boolean isSuccess(Integer productId,Object[][] likedProd);
}
