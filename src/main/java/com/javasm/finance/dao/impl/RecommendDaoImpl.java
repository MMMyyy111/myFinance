package com.javasm.finance.dao.impl;

import com.javasm.finance.dao.RecommendDao;
import com.javasm.finance.entity.Product;
import com.javasm.finance.entity.ProductRecommend;
import com.javasm.finance.util.DButils2;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendDaoImpl implements RecommendDao {
    QueryRunner run = new QueryRunner(DButils2.getDruidDataSource());
    @Override
    public List<ProductRecommend> queryRecommend(Integer page,Integer pageSize,String productName) throws SQLException {
        BeanListHandler<ProductRecommend> listHandler = new BeanListHandler<>(ProductRecommend.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        boolean isId = true;
        String sql = "SELECT pr.product_id,pb.product_name,pr.recommend_rate,pr.is_first,pr.is_online,pr.is_visible_customer,pr.recommend_reason " +
                "FROM fin_product_recommend pr LEFT JOIN fin_product_basic pb " +
                "ON pr.product_id=pb.product_id ";
        List paras = new ArrayList();
        if(productName!=null && !"".equals(productName)){
            if(isId){
                sql += "WHERE product_name LIKE ? ";
                isId = false;
                paras.add("%"+productName+"%");
            }
        }
        sql += "limit "+(page-1)*pageSize+","+pageSize;
        List<ProductRecommend> productRecommendList = run.query(sql, listHandler, paras.toArray());
        return productRecommendList;
    }

    @Override
    public long recommendTotal(String productName) throws SQLException {
        String sql = "SELECT COUNT(1) FROM fin_product_recommend pr LEFT JOIN fin_product_basic pb " +
                "ON pr.product_id = pb.product_id ";
        List paras = new ArrayList();
        if(productName!=null && !"".equals(productName)){
            sql += "WHERE pb.product_name LIKE ? ";
            paras.add("%"+productName+"%");
        }
        // ScalarHandler：查询单数据
        long i = run.query(sql, new ScalarHandler<>(), paras.toArray());
        return i;
    }

    @Override
    public List<Product> queryNotRecom() throws SQLException {
        BeanListHandler<Product> listHandler = new BeanListHandler<>(Product.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        String sql = "SELECT product_id,product_name FROM fin_product_basic " +
                "WHERE product_id NOT IN (SELECT product_id FROM fin_product_recommend) AND audit_status=2";
        List<Product> notRecomList = run.query(sql, listHandler);
        return notRecomList;
    }

    @Override
    public ProductRecommend queryRecomById(Integer productId) throws SQLException {
        String sql = "SELECT pr.product_id,pb.product_name,pr.recommend_rate,pr.is_first,pr.is_online,pr.is_visible_customer,pr.recommend_reason,pr.version_id FROM fin_product_recommend pr LEFT JOIN fin_product_basic pb " +
                "ON pr.product_id=pb.product_id WHERE pr.product_id=? ";
        BeanHandler<ProductRecommend> beanHandler = new BeanHandler<>(ProductRecommend.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        ProductRecommend productRecommend = run.query(sql, beanHandler, productId);
        return productRecommend;
    }

    @Override
    public ProductRecommend queryRecomByPidVid(Integer productId, Integer versionId) throws SQLException {
        String sql = "SELECT pr.product_id,pb.product_name,pr.recommend_rate,pr.is_first,pr.is_online,pr.is_visible_customer,pr.recommend_reason,pr.version_id FROM fin_product_recommend pr LEFT JOIN fin_product_basic pb " +
                "ON pr.product_id=pb.product_id WHERE pr.product_id=? AND pr.version_id=?";
        BeanHandler<ProductRecommend> beanHandler = new BeanHandler<>(ProductRecommend.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        ProductRecommend productRecommend = run.query(sql, beanHandler, productId,versionId);
        return productRecommend;
    }

    @Override
    public int addRecommend(ProductRecommend productRecommend) throws SQLException {
        String sql = "INSERT INTO fin_product_recommend(product_id,product_category_id,recommend_rate,is_first,is_online,is_visible_customer,recommend_reason,version_id) VALUES(?, " +
                "(SELECT product_category_id FROM fin_product_basic WHERE product_id=?)," +
                "?,?,?,?,?,100)";
        int i = run.update(sql, productRecommend.getProductId(),productRecommend.getProductId(), productRecommend.getRecommendRate(), productRecommend.getIsFirst(), productRecommend.getIsOnline(), productRecommend.getIsVisibleCustomer(), productRecommend.getRecommendReason());
        return i;
    }

    @Override
    public int editRecommend(ProductRecommend productRecommend) throws SQLException {
        String sql = "UPDATE fin_product_recommend SET recommend_rate=?,is_first=?,is_online=?,is_visible_customer=?,recommend_reason=?,version_id=?+1 WHERE product_id=?";
        int i = run.update(sql, productRecommend.getRecommendRate(), productRecommend.getIsFirst(), productRecommend.getIsOnline(), productRecommend.getIsVisibleCustomer(), productRecommend.getRecommendReason(),productRecommend.getVersionId(), productRecommend.getProductId());
        return i;
    }

    @Override
    public List<Product> querySeriesProd(Integer productId) throws SQLException {
        BeanListHandler<Product> listHandler = new BeanListHandler<>(Product.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        String sql = "SELECT product_id,product_name FROM fin_product_basic WHERE audit_status=2 " +
                "AND product_category_id=(SELECT product_category_id FROM fin_product_basic WHERE product_id = ? ) " +
                "AND product_id != ? ";
        List<Product> seriesProdList = run.query(sql, listHandler, productId,productId);
        return seriesProdList;
    }

    @Override
    public List<Integer> queryLinkedId(Integer productId) throws SQLException {
        ColumnListHandler<Integer> linkedProductId = new ColumnListHandler<>("linked_product_id");
        String sql = "SELECT linked_product_id FROM fin_product_linked WHERE product_id = ?";
        List<Integer>  linkedList = run.query(sql, linkedProductId, productId);
        return linkedList;
    }

    @Override
    public int deleteLinked(Integer productId,Connection connection) throws SQLException {
        String sql = "DELETE FROM fin_product_linked WHERE product_id = ?";
        int i = run.update(connection, sql,productId);
        return i;
    }

    @Override
    public int addlinked(Object[][] linkedProd, Connection connection) throws SQLException {
        String sql = "INSERT INTO fin_product_linked(product_id,linked_product_id) VALUES(?,?)";
        int rows = run.batch(connection, sql,linkedProd).length;
        return rows;
    }
}
