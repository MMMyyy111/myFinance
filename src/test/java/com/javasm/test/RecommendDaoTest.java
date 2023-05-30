package com.javasm.test;

import com.javasm.finance.dao.RecommendDao;
import com.javasm.finance.dao.impl.RecommendDaoImpl;
import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductRecommend;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class RecommendDaoTest {
    @Test
    public void test(){
        RecommendDao recommendDao = new RecommendDaoImpl();
        try {
            System.out.println(recommendDao.querySeriesProd(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
