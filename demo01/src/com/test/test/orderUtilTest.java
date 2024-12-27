package com.test.test;

import com.test.util.OrderUtils;

import java.math.BigInteger;

public class orderUtilTest {
    public static void main(String[] args) {
        String s = OrderUtils.getOrderCode(1);
        System.out.println(s);
        Long oid = Long.valueOf(s.substring(15,30));
        System.out.println(oid);
    }
}
