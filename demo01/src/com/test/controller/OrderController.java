package com.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Order;
import com.test.pojo.User;
import com.test.service.OrderService;
import com.test.service.impl.OrderServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.JwtTokenUtils;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@WebServlet("/order/*")
public class OrderController extends BaseController {
    private OrderService orderService = new OrderServiceImpl();

    /**
     *添加订单，传入用户token，商品id: sid,数量num，下单日期date
     */
    protected void orderShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Integer sid = Integer.parseInt(req.getParameter("sid"));
        Integer uid = user.getId();
        Integer num = Integer.parseInt(req.getParameter("num"));
        String date = req.getParameter("date");
        // 调用服务层方法完成下单操作
        int rows = orderService.orderShangpin(sid,uid,num,date);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    /**
     *删除订单，传入用户token,订单id
     */
    protected void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        String username = orderService.findMUsernameByOid(oid);
        Result result = Result.build(null, ResultCodeEnum.DELETION_FAILED);
        if (user.getUsername().equals(username)) {
            int rows = orderService.deleteOrder(oid);
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *支付订单，传入买家用户token，商品集合orderList(购物车或直接购买)
     */
    protected void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Integer uid = user.getId();
        List<Integer> oidList = WebUtil.readSetByJson(req,Integer.class);
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        int rows = 0;
        for (Integer oid : oidList) {
           Order order = orderService.findOrderById(oid);
           if(order != null&& !Objects.equals(order.getUserId(), uid)) {
               oidList = null;
               break;
           }
        }
        if (oidList != null) {
            rows = orderService.payOrder(oidList);
            if (rows > 0) {
                result = Result.ok(rows);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *发货，传入商家用户token,订单id
     */
    protected void deliverOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user.getType().equals("merchant")||user.getType().equals("admin")) {
            Integer oid = Integer.parseInt(req.getParameter("oid"));
            String username = orderService.getMUsernameByOid(oid);
            int rows = 0;
            if (user.getUsername().equals(username)) {
                rows = orderService.deliverOrder(oid);
                result = Result.ok(rows);
            }else {
                result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *收货，传入买家用户token
     */
    protected void completeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer oid = Integer.parseInt(req.getParameter("oid"));
        int rows = orderService.completeOrder(oid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据买家uid查找发送的订单，传入token
     */
    protected void findOrderByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Integer uid = user.getId();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        List<Order> orderList = orderService.findOrderByUid(uid);
        if (orderList != null) {
            result = Result.ok(orderList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据oid返回订单
     */
    protected void findOrderById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int oid = Integer.parseInt(req.getParameter("oid"));
        Order order = orderService.findOrderById(oid);
        Result result = Result.ok(order);
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据商家用户username返回所接收的订单列表,传入token
     */
    protected void findOrderByMUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user.getType().equals("merchant")||user.getType().equals("admin")) {
            List<Order> orderList= orderService.getOrderByMUsername(user.getUsername());
            if (orderList != null) {
                result = Result.ok(orderList);
            }else{
                result = Result.build(null,ResultCodeEnum.NOT_FOUND);
            }
        }
        WebUtil.writeJson(resp,result);
    }
}
