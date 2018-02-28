package com.wpx.service;

import com.wpx.domain.Order;
import com.wpx.domain.PageBean;
import com.wpx.domain.User;

public interface OrderService {
    void add(Order order) throws  Exception;

    PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

    Order getById(String oid) throws Exception;

    void update(Order order) throws Exception;
}
