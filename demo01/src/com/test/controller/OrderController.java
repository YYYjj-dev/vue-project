package com.test.controller;

import com.test.common.Result;
import com.test.pojo.Order;
import com.test.service.OrderService;
import com.test.service.impl.OrderServiceImpl;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


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
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    protected void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        int rows = orderService.deleteOrder(oid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    protected void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        int rows = orderService.payOrder(oid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    protected  void deliverOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        int rows = orderService.deliverOrder(oid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }
    protected void completeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        int rows = orderService.completeOrder(oid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    protected void findOrderByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Integer.parseInt(req.getParameter("uid"));
        List<Order> orderList = orderService.findOrderByUid(uid);
        Result result = Result.ok(orderList);
        WebUtil.writeJson(resp,result);
    }
    protected void findOrderById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int oid = Integer.parseInt(req.getParameter("oid"));
        Order order = orderService.findOrderById(oid);
        Result result = Result.ok(order);
        WebUtil.writeJson(resp,result);
    }


    /**
     *根据商家id返回订单
     */
    protected void findOrderByMid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer mid = Integer.parseInt(req.getParameter("id"));
        List<Order> orderList= orderService.findOrderByMid(mid);
        Result result = Result.ok(orderList);
        WebUtil.writeJson(resp,result);
    }
}
