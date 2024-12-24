package com.test.controller;

import com.test.service.OrderService;
import com.test.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/order/*")
public class OrderController extends BaseController {
    private OrderService orderService = new OrderServiceImpl();

    protected void orderShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer sid = Integer.parseInt(req.getParameter("sid"));
        Integer uid = Integer.parseInt(req.getParameter("uid"));
        Integer num = Integer.parseInt(req.getParameter("num"));
        String date = req.getParameter("date");
        // 调用服务层方法完成下单操作
        int rows = orderService.orderShangpin(sid,uid,num,date);
    }


}
