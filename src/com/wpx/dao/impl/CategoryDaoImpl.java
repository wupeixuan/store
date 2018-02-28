package com.wpx.dao.impl;

import com.wpx.dao.CategoryDao;
import com.wpx.domain.Category;
import com.wpx.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    /**
     * 查询所有的分类
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Category> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return qr.query(sql, new BeanListHandler<>(Category.class));
    }

    /**
     * 添加分类
     *
     * @param c
     * @throws Exception
     */
    @Override
    public void add(Category c) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into category values(?,?)";
        qr.update(sql, c.getCid(), c.getCname());
    }

    /**
     * 通过cid获取分类
     *
     * @param cid
     * @return
     * @throws Exception
     */
    @Override
    public Category getById(String cid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category where cid = ? limit 1";
        return qr.query(sql, new BeanHandler<>(Category.class), cid);

    }

    /**
     * 更新分类
     *
     * @param category
     * @throws Exception
     */
    @Override
    public void update(Category category) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update category set cname = ? where cid = ?";
        qr.update(sql, category.getCname(), category.getCid());
    }

    /**
     * 删除分类
     *
     * @param cid
     * @throws Exception
     */
    @Override
    public void delete(String cid) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from category  where cid = ?";
        qr.update(DataSourceUtils.getConnection(), sql, cid);
    }

}
