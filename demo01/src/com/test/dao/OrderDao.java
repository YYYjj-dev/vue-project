package com.test.dao;

import com.test.pojo.Order;

import java.util.List;

public interface OrderDao {
    //用户下单

    int deleteOrder(Integer oid);

    int payOrder(List<Integer> oid);

    List<Order> findOrderByMid(Integer mid);

    List<Order> findOrderByUid(int uid);

    Order findOrderById(int oid);

    int deliverOrder(Integer oid);

    int completeOrder(Integer oid);

    String findMUsernameByOid(Integer oid);

    Integer getMidByUsername(String username);

    List<Order> getOrderByMUsername(String username);

    String getMUsernameByOid(Integer oid);

    int orderShangpin(Order orderInfo);

    int purchase(Order orderInfo);
}
