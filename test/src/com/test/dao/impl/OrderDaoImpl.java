package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.OrderDao;
import com.test.util.OrderUtils;

public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public int orderShangpin(Integer sid, Integer uid, Integer num,String date) {

        String sql = "select num from shangpin where id = ?";
        int shangpinNum = baseUpdate(sql,sid);
        if(shangpinNum - num > 0) {
            return num;
        }else{
            Integer oid = Integer.valueOf(OrderUtils.getOrderCode(uid));

            String sql1 = "update shangpin set num = num - ? where id =?";
            baseUpdate(sql1,num,sid);

            String sql2 = "select price from shangpin where id = ?";
            Double price = (Double) baseQuery(Double.class, sql2, sid).get(0);

            String sql3 = "insert into orderdetail values(DEFAULT,?,?,?,?)";
            baseUpdate(sql3,oid,sid,num,price);

            String sql4 = "select id from orderdetail where order_id =?";
            int xid = baseUpdate(sql4,oid);

            String sql5 = "insert into order values(DEFAULT,?,?,?)";
            return baseUpdate(sql5,date,xid,uid);
        }
    }

    @Override
    public int deleteOrder(Integer oid) {
        String sql = "delete from orderdetail where order_id =?";
        String sql1 = "delete from order where xiangqing_id =?";
        baseUpdate(sql,oid);
        return baseUpdate(sql1,oid);
    }
}
