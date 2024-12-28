package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.OrderDao;
import com.test.pojo.Order;
import com.test.util.OrderUtils;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public int orderShangpin(Integer sid, Integer uid, Integer num,String date) {
        String sql1="select price from shangpin where id=?";

        double price=(Double)baseQuery(Double.class,sql1,sid).get(0);
        double sub = num *price;
        System.out.println(sub);

        String sql2="insert into `order` values(DEFAULT,?,?,?,?,?,?)";
        Object[] params={date,uid,sub,num,sid,"未支付"};
        return baseUpdate(sql2, params);
    }

    @Override
    public int deleteOrder(Integer oid) {
        String sql = "delete from `order` where id =?";
        return baseUpdate(sql,oid);
    }

    @Override
    public int payOrder(Integer oid) {
        String sql = "update `order` set status = '已支付' where order_id =?";
        return baseUpdate(sql,oid);
    }

    @Override
    public List<Order> findOrderByMid(Integer mid) {
        String sql= "select `order`.id,date,user_id,sub,quantity,shangpinId,status " +
                "from `order`,shangpin,merchant " +
                "where `order`.shangpinId = shangpin.id and shangpin.store_id=merchant.id=?";
        List<Order> orderList = baseQuery(Order.class,sql,mid);
        return orderList;
    }

    @Override
    public List<Order> findOrderByUid(int uid) {
        String sql = "select id,date,user_id userId,sub,quantity,shangpinId,status from `order` where user_id=?";
        List<Order> orderList = baseQuery(Order.class,sql,uid);
        return orderList;
    }

    @Override
    public Order findOrderById(int oid) {
        String sql = "select id,date,user_id userId,sub,quantity,shangpinId,status from `order` where id=?";
        List<Order> orderList = baseQuery(Order.class,sql,oid);
        return orderList.isEmpty() ? null : orderList.get(0);
    }

    @Override
    public int deliverOrder(Integer oid) {
        String sql = "update `order` set status = '已发货' where order_id =?";
        return baseUpdate(sql,oid);
    }

    @Override
    public int completeOrder(Integer oid) {
        String sql = "update `order` set status = '已收货' where order_id =?";
        return baseUpdate(sql,oid);
    }

}
