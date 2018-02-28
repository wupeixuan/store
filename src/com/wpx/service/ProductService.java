package com.wpx.service;

import com.wpx.domain.PageBean;
import com.wpx.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findNew() throws Exception;

    List<Product> findHot() throws Exception;

    Product getByPid(String pid) throws Exception;

    PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

    List<Product> findAll() throws Exception;

    void add(Product p) throws Exception;

    void delete(String pid) throws Exception;

    Product getById(String pid) throws Exception;

    void update(Product product) throws Exception;
}
