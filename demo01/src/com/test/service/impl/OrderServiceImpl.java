package com.test.service.impl;

import com.test.dao.OrderDao;
import com.test.dao.impl.OrderDaoImpl;
import com.test.service.OrderService;

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
}
