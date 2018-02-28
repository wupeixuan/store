package com.wpx.dao;

import com.wpx.domain.Order;
import com.wpx.domain.OrderItem;

import java.util.List;

public interface OrderDao {


    void add(Order order) throws Exception;

    void addItem(OrderItem oi) throws Exception;

    List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception;

    int getTotalCount(String uid) throws Exception;

    Order getById(String oid) throws Exception;

    void update(Order order) throws Exception;

    List<Order> findAllByState(String state) throws Exception;
}
