package com.test.service.impl;

import com.test.dao.OrderDao;
import com.test.dao.impl.OrderDaoImpl;
import com.test.pojo.Order;
import com.test.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao=new OrderDaoImpl();
    @Override
    public int deleteOrder(Integer oid) {
        return orderDao.deleteOrder(oid);
    }

    @Override
    public int payOrder(List<Integer> oid) {
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

    @Override
    public int deliverOrder(Integer oid) {
        return orderDao.deliverOrder(oid);
    }

    @Override
    public int completeOrder(Integer oid) {
        return orderDao.completeOrder(oid);
    }

    @Override
    public String findMUsernameByOid(Integer oid) {
        return orderDao.findMUsernameByOid(oid);
    }

    @Override
    public Integer getMidByUsername(String username) {
        return orderDao.getMidByUsername(username);
    }

    @Override
    public List<Order> getOrderByMUsername(String username) {
        return orderDao.getOrderByMUsername(username);
    }

    @Override
    public String getMUsernameByOid(Integer oid) {
        return orderDao.getMUsernameByOid(oid);
    }

    @Override
    public int orderShangpin(Order orderInfo) {
        return orderDao.orderShangpin(orderInfo);
    }
}
