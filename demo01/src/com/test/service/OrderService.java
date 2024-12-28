package com.test.service;

import com.test.pojo.Order;

import java.util.List;

public interface OrderService {
    int orderShangpin(Integer sid, Integer uid, Integer num, String date);

    int deleteOrder(Integer oid);

    int payOrder(Integer oid);

    List<Order> findOrderByMid(Integer mid);

    List<Order> findOrderByUid(int uid);

    Order findOrderById(int oid);

    int deliverOrder(Integer oid);

    int completeOrder(Integer oid);
}
