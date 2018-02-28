package com.wpx.service.impl;

import com.wpx.dao.ProductDao;
import com.wpx.domain.PageBean;
import com.wpx.domain.Product;
import com.wpx.service.ProductService;
import com.wpx.utils.BeanFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    /**
     * 查询最新
     */
    @Override
    public List<Product> findNew() throws Exception {
        ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
        return pdao.findNew();
    }

    /**
     * 查询热门
     */
    @Override
    public List<Product> findHot() throws Exception {
        ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
        return pdao.findHot();
    }

    /**
     * 查询单个商品
     */
    @Override
    public Product getByPid(String pid) throws Exception {
        ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
        return pdao.getByPid(pid);
    }

    /**
     * 按类别分页查询商品
     */
    @Override
    public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
        ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
        //当前页数据
        List<Product> list = pdao.findByPage(currPage, pageSize, cid);

        //总条数
        int totalCount = pdao.getTotalCount(cid);

        return new PageBean<>(list, currPage, pageSize, totalCount);
    }

    /**
     * 查询所有商品
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findAll() throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.findAll();
    }

    /**
     * 添加商品
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void add(Product p) throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        pd.add(p);
    }

    /**
     * 删除商品
     *
     * @param pid
     * @throws Exception
     */
    @Override
    public void delete(String pid) throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        pd.delete(pid);
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
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.getById(pid);
    }

    /**
     * 更新商品
     *
     * @param product
     * @throws Exception
     */
    @Override
    public void update(Product product) throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        pd.update(product);
    }

}
