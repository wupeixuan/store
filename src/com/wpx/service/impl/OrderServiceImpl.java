package com.wpx.service.impl;

import com.wpx.dao.OrderDao;
import com.wpx.domain.Order;
import com.wpx.domain.OrderItem;
import com.wpx.domain.PageBean;
import com.wpx.domain.User;
import com.wpx.service.OrderService;
import com.wpx.utils.BeanFactory;
import com.wpx.utils.DataSourceUtils;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    /**
     * 生成订单
     *
     * @param order
     * @throws Exception
     */
    @Override
    public void add(Order order) throws Exception {
        try {
            //1.开启事务
            DataSourceUtils.startTransaction();
            OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");

            //2.向orders表中添加一个数据
            od.add(order);

            //3.向orderitem中添加n条数据
            for (OrderItem oi : order.getItems()
                    ) {
                od.addItem(oi);
            }
            //4.事务处理
            DataSourceUtils.commitAndClose();
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.rollbackAndClose();
            throw e;
        }

    }

    /**
     * 分页查询订单
     *
     * @param currPage
     * @param pageSize
     * @param user
     * @return
     */
    @Override
    public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        // 查询当前页数据
        List<Order> list = od.findAllByPage(currPage, pageSize, user.getUid());
        // 查询总条数
        int totalCount = od.getTotalCount(user.getUid());
        return new PageBean<>(list, currPage, pageSize, totalCount);
    }

    /**
     * 查询订单详情
     *
     * @param oid
     * @return
     */
    @Override
    public Order getById(String oid) throws Exception {
        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        return od.getById(oid);
    }

    @Override
    public void update(Order order) throws Exception {
        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        od.update(order);
    }
}
