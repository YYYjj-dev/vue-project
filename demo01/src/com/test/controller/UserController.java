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
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")

@WebServlet("/user/*")
public class UserController extends BaseController {

    private UserService userService = new UserServiceImpl();

    /**
     * 注册业务
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();
        JakartaServletFileUpload servletFileUpload = new JakartaServletFileUpload(diskFileItemFactory);
        User user = null;
        if (servletFileUpload.isMultipartContent(req)) {
            user = ImgUtil.updateUser(req);
        } else {
            user = WebUtil.readJson(req, User.class);
        }
        String type = user.getType();
        int rows = 0;
        if (type.equals("normaluser")) {
            rows = userService.normalRegist(user);
        } else if (type.equals("merchant")) {
            rows = userService.merchantRegist(user);
        }
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     * 登录业务
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) {

        User User = WebUtil.readJson(req, User.class);
        User loginUser = userService.findByUsername(User.getUsername());
        Result result = null;
        if (null == loginUser) {
            result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        } else if (!(User.getPassword().equals(loginUser.getPassword()))) {
            result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        } else {
            loginUser.setPassword("");
            result = Result.ok(loginUser);
        }
        WebUtil.writeJson(resp, result);
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
        Result result = Result.ok(null);
        if (null != byUsername) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
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
        Integer mid = Integer.valueOf(req.getParameter("mid"));
        int rows = userService.addCollect(uid, mid);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp, result);
    }

    /**
     * 展示收藏
     */
    protected void findCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer uid = Integer.valueOf(req.getParameter("uid"));
        List<Merchant> collectList = userService.findCollect(uid);
        Result result = Result.ok(collectList);
        WebUtil.writeJson(resp, result);
    }

    /**
     *更新用户信息，传入用户对象,失败返回业务码506
     */
    protected void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = ImgUtil.updateUser(req);
        int rows = 0;
        if(user.getType().equals("normaluser")) {
            rows = userService.updateNormal(user);
        }else if(user.getType().equals("merchant")) {
            rows = userService.updateMerchant(user);
        }
        Result result = Result.build(null, ResultCodeEnum.UPDATE_USER_FAILED);
        if(rows >= 1) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp, result);
    }

}
