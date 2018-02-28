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

    /**
     * 添加商品
     *
     * @param p
     */
    @Override
    public void add(Product p) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?);";
        qr.update(sql, p.getPid(), p.getPname(), p.getMarket_price(),
                p.getShop_price(), p.getPimage(), p.getPdate(),
                p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCategory().getCid());
    }

    /**
     * 删除商品
     *
     * @param pid
     * @throws Exception
     */
    @Override
    public void delete(String pid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from product  where pid = ?";
        qr.update(sql, pid);
    }

    /**
     * 通过pid获取商品
     *
     * @param pid
     * @return
     * @throws Exception
     */
    @Override
    public Product getById(String pid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ? limit 1";
        return qr.query(sql, new BeanHandler<>(Product.class), pid);
    }

    /**
     * 更新商品
     *
     * @param product
     * @throws Exception
     */
    @Override
    public void update(Product product) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set pname = ? ,market_price = ? ,shop_price = ? ,pdesc = ? ,is_hot = ? ,pimage =? ,cid = ? where pid = ?";
        qr.update(sql, product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPdesc(), product.getIs_hot(), product.getPimage(), product.getCategory().getCid(), product.getPid());
    }

}
