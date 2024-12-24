package com.test.controller;


import com.test.pojo.Shangpin;
import com.test.pojo.User;
import com.test.service.ShangpinService;
import com.test.service.impl.ShangpinServiceImpl;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/shangpin/*")
public class ShangpinController extends BaseController{

    private ShangpinService shangpinService = new ShangpinServiceImpl();

    public void addShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = WebUtil.readJson(req,User.class);
    }


    protected void findAllShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Shangpin> shangpinList = shangpinService.findAllShangpin();
    }

    protected void findShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Shangpin shangpin = shangpinService.findShangpinById(id);
    }

    protected void deleteShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = shangpinService.deleteShangpinById(id);

    }
}
