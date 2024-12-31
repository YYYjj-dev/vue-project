package com.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Order;
import com.test.pojo.TokenInfo;
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

@SuppressWarnings("all")
@WebServlet("/order/*")
public class OrderController extends BaseController {
    private OrderService orderService = new OrderServiceImpl();

    /**
     *添加订单，传入用户token，订单对象：date，商品id：shangpinId，购买数量quantity
     */
    protected void orderShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Order orderInfo = tokenInfo.getOrderInfo();
        orderInfo.setUserId(user.getId());
        // 调用服务层方法完成下单操作
        int rows = orderService.orderShangpin(orderInfo);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    /**
     *删除订单，传入用户token,订单id
     */
    protected void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Integer oid = tokenInfo.getId();
        String username = orderService.findMUsernameByOid(oid);
        Result result = Result.build(null, ResultCodeEnum.DELETION_FAILED);
        int rows = orderService.deleteOrder(oid);
        result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    /**
     *直接购买，创建已支付订单，传入用户token，订单对象：date，商品id：shangpinId，购买数量quantity
     */
    protected void purchase(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Order orderInfo = tokenInfo.getOrderInfo();
        orderInfo.setUserId(user.getId());
        // 调用服务层方法完成下单操作
        int rows = orderService.purchase(orderInfo);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }
    /**
     *支付订单，传入买家用户token，商品id集合oidList(购物车或直接购买)
     */
    protected void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        List<Integer> oidList = tokenInfo.getOidList();
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        int rows = 0;
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
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Integer oid = tokenInfo.getId();
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user.getType().equals("merchant")||user.getType().equals("admin")) {
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
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Integer oid = tokenInfo.getId();
        if( user.getType().equals("merchant")||user.getType().equals("admin")) {
            int rows = orderService.completeOrder(oid);
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    /**
     *根据买家uid查找发送的订单，传入token
     */
    protected void findOrderByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();

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
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        if(token == null)
        {
            token = WebUtil.readJson(req,String.class);
        }
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
