package com.test.dao;

public interface OrderDao {
    //用户下单
    int orderShangpin(Integer sid, Integer uid, Integer num, String date);
}
