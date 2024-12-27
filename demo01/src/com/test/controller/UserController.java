package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.User;
import com.test.service.UserService;
import com.test.service.impl.UserServiceImpl;
import com.test.util.WebUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/user/*")
public class UserController extends BaseController{

    private UserService userService = new UserServiceImpl();

    /**
     * 注册业务
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        User user = WebUtil.readJson(req,User.class);
        int rows = userService.regist(user);
        Result result =Result.ok(null);
        if(rows < 1){
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 登录业务
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp){

        User User = WebUtil.readJson(req,User.class);
        User loginUser = userService.findByUsername(User.getUsername());
        Result result =null;
        if(null==loginUser){
            result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        } else if(!(User.getPassword().equals(loginUser.getPassword()))) {
            result = Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }else{
            loginUser.setPassword("");
            result = Result.ok(loginUser);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 检查是否有重复用户名
     */
    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //接收用户名
        String username = req.getParameter("username");
        //查询用户名是否有对应的用户
        User byUsername = userService.findByUsername(username);
        //如果有响应已占有
        //如果没用响应可用
        Result result= Result.ok(null);
        if(null!=byUsername){
            result=Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String info = objectMapper.writeValueAsString(result);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(info);
    }


    /**
     * 添加收藏
     */
    protected void addCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        Integer sid = Integer.valueOf(req.getParameter("sid"));
        int rows = userService.addCollect(uid,sid);
        Result result =Result.ok(rows);
    }

    protected void findCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        int rows = userService.findCollect(uid);
    }


}
