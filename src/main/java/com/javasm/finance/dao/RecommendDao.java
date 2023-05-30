package com.javasm.finance.dao;

import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductRecommend;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RecommendDao {
    List<ProductRecommend> queryRecommend(Integer page,Integer pageSize,String productName) throws SQLException;
    long recommendTotal(String productName) throws SQLException;
    List<Product> queryNotRecom() throws SQLException;
    ProductRecommend queryRecomById(Integer productId) throws SQLException;
    int addRecommend(ProductRecommend productRecommend) throws SQLException;
    int editRecommend(ProductRecommend productRecommend) throws SQLException;
    ProductRecommend queryRecomByPidVid(Integer productId,Integer versionId) throws SQLException;
    List<Product> querySeriesProd(Integer productId) throws SQLException;
    List<Integer> queryLinkedId(Integer productId) throws SQLException;
    int deleteLinked(Integer productId,Connection connection) throws SQLException;
    int addlinked(Object[][] likedProd, Connection connection) throws SQLException;
}
