package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Merchant;
import com.test.pojo.User;
import com.test.service.UserService;
import com.test.service.impl.UserServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")

@WebServlet("/user/*")
public class UserController extends BaseController {

    private UserService userService = new UserServiceImpl();

    /**
     * 注册业务
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        User user = ImgUtil.updateUser(req);
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

        User user = WebUtil.readJson(req,User.class);
        User loginUser = userService.findByUsername(user.getUsername());
        Result result =null;

        if(null==loginUser){
            result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        } else if(!(user.getPassword().equals(loginUser.getPassword()))) {
            result = Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }else{
            loginUser.setPassword("");
            result = Result.ok(loginUser);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *更改用户个人信息
     */
    protected void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = ImgUtil.updateUser(req);
        User loginUser = userService.findByUsername(user.getUsername());
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        int rows = userService.updateUser(user);
        if(rows >0){
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *修改用户密码，需要传入username,oldPassword和newPassword
     */
    protected void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        User loginUser = userService.findByUsername(username);
        if(!(oldPassword.equals(loginUser.getPassword()))){
            result = Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }else {
            int rows = userService.updatePassword(newPassword);
            if(rows >0){
                result = Result.ok(rows);
            }
        }
        WebUtil.writeJson(resp,result);

    }

    /**
     *删除用户
     */
    protected void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        int rows = userService.deleteUser(username);
        Result result =Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if(rows >0){
            result = Result.ok(rows);
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
     * 添加收藏,传入店铺mid和用户uid
     */
    protected void addCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        Integer mid = Integer.valueOf(req.getParameter("mid"));
        int rows = userService.addCollect(uid, mid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp, result);
    }

    /**
     * 展示收藏,传入用户uid
     */
    protected void findCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        List<Merchant> collectList = userService.findCollect(uid);
        Result result = Result.ok(collectList);
        WebUtil.writeJson(resp, result);
    }

    /**
     *根据用户类型（type）返回对应的用户
     */
    protected void foundUserByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        List<User> userList = userService.foundUserByType(type);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(userList.isEmpty()){
            result=Result.ok(userList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *查找所有的用户
     */
    protected void findAllUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> userList = userService.findAllUser();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(!userList.isEmpty()){
            result=Result.ok(userList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", req.getParameter("id"));
        queryParams.put("type", req.getParameter("type"));
        queryParams.put("username", req.getParameter("username"));
        queryParams.put("gender", req.getParameter("gender"));
        List<User> userList = userService.findUser(queryParams);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(userList.isEmpty()){
            result=Result.ok(userList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findUserById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        User userInfo = userService.findUserById(uid);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(null!=userInfo){
            result=Result.ok(userInfo);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = ImgUtil.updateUser(req);
        int rows = userService.addUser(user);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if(rows >0){
            result = Result.ok(rows);
        }else {
            result = Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp,result);
    }
}

