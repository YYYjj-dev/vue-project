package com.test.service.impl;

import com.test.dao.OrderDao;
import com.test.dao.impl.OrderDaoImpl;
import com.test.pojo.Order;
import com.test.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao=new OrderDaoImpl();
    @Override
    public int orderShangpin(Integer sid, Integer uid, Integer num, String date) {
            return orderDao.orderShangpin(sid,uid,num,date);
    }

    @Override
    public int deleteOrder(Integer oid) {
        return orderDao.deleteOrder(oid);
    }

    @Override
    public int payOrder(Integer oid) {
        return orderDao.payOrder(oid);
    }

    @Override
    public List<Order> findOrderByMid(Integer mid) {
        return orderDao.findOrderByMid(mid);
    }

    @Override
    public List<Order> findOrderByUid(int uid) {
        return orderDao.findOrderByUid(uid);
    }

    @Override
    public Order findOrderById(int oid) {
        return orderDao.findOrderById(oid);
    }
}
