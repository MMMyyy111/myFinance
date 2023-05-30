package com.javasm.finance.service.impl;

import com.javasm.finance.dao.RecommendDao;
import com.javasm.finance.dao.impl.RecommendDaoImpl;
import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductRecommend;
import com.javasm.finance.service.RecommendService;
import com.javasm.finance.util.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RecommendServiceImpl implements RecommendService {
    RecommendDao recommendDao = new RecommendDaoImpl();

    @Override
    public List<ProductRecommend> queryRecommend(Integer page, Integer pageSize, String productName) {
        List<ProductRecommend> productRecommendList = null;
        try {
            productRecommendList = recommendDao.queryRecommend(page, pageSize, productName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productRecommendList;
    }

    @Override
    public long recommendTotal(String productName) {
        long i = 0;
        try {
            i = recommendDao.recommendTotal(productName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Product> queryNotRecom() {
        List<Product> queryNotRecom = null;
        try {
            queryNotRecom = recommendDao.queryNotRecom();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return queryNotRecom;
    }

    @Override
    public ProductRecommend queryRecomById(Integer productId) {
        ProductRecommend productRecommend = null;
        try {
            productRecommend = recommendDao.queryRecomById(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productRecommend;
    }

    @Override
    public ProductRecommend queryRecomByPidVid(Integer productId, Integer versionId) {
        ProductRecommend productRecommend = null;
        try {
            productRecommend = recommendDao.queryRecomByPidVid(productId, versionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productRecommend;
    }

    @Override
    public int addRecommend(ProductRecommend productRecommend) {
        int i = 0;
        try {
            i = recommendDao.addRecommend(productRecommend);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public int editRecommend(ProductRecommend productRecommend) {
        int i = 0;
        try {
            i = recommendDao.editRecommend(productRecommend);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Product> querySeriesProd(Integer productId) {
        List<Product> seriesProdList = null;
        try {
            seriesProdList = recommendDao.querySeriesProd(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return seriesProdList;
    }

    @Override
    public List<Integer> queryLinkedId(Integer productId) {
        List<Integer> linkedIdList= null;
        try {
            linkedIdList = recommendDao.queryLinkedId(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linkedIdList;
    }

    @Override
    public boolean isSuccess(Integer productId, Object[][] linkedProd) {
        boolean isSuccess = false;
        Connection conn = DbUtils.getConn();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            List<Integer> linkedId = recommendDao.queryLinkedId(productId);
            if(linkedId!=null && linkedId.size()>0){
                recommendDao.deleteLinked(productId,conn);
            }
            recommendDao.addlinked(linkedProd,conn);
            org.apache.commons.dbutils.DbUtils.commitAndCloseQuietly(conn);
            isSuccess = true;
        } catch (SQLException throwables) {
            org.apache.commons.dbutils.DbUtils.rollbackAndCloseQuietly(conn);
        }
        return isSuccess;
    }
}
