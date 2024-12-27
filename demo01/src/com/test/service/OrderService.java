package com.test.service;

public interface OrderService {
    int orderShangpin(Integer sid, Integer uid, Integer num, String date);

    int deleteOrder(Integer oid);
}
