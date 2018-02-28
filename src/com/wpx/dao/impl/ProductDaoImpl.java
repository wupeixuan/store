package com.wpx.dao.impl;

import com.wpx.dao.ProductDao;
import com.wpx.domain.Product;
import com.wpx.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    /**
     * 查询最新
     */
    @Override
    public List<Product> findNew() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product order by pdate desc limit 9";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }

    /**
     * 查询热门
     */
    @Override
    public List<Product> findHot() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = 1 order by pdate desc limit 9";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }

    /**
     * 通过商品id 获取商品详情
     */
    @Override
    public Product getByPid(String pid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from product where pid = ? limit 1";
        return qr.query(sql, new BeanHandler<>(Product.class), pid);
    }

    /**
     * 查询当前也需要展示的数据
     */
    @Override
    public List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid = ? limit ?,?";
        return qr.query(sql, new BeanListHandler<>(Product.class), cid, (currPage - 1) * pageSize, pageSize);
    }

    /**
     * 查询当前类别的总条数
     */
    @Override
    public int getTotalCount(String cid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        return ((Long) qr.query(sql, new ScalarHandler(), cid)).intValue();
    }

    /**
     * 更新商品的cid 为删除分类的时候准备
     *
     * @param cid
     * @throws Exception
     */
    @Override
    public void updateCid(String cid) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "update product set cid = null where cid = ?";
        qr.update(DataSourceUtils.getConnection(), sql, cid);
    }

    /**
     * 查询所有商品
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }


}
