package com.test.controller;


import com.test.common.Result;
import com.test.pojo.News;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/shangpin/*")
public class ShangpinController extends BaseController{

    private ShangpinService shangpinService = new ShangpinServiceImpl();

    public void addShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Shangpin shangpin = new Shangpin();
        int rows = shangpinService.addShangpin(shangpin);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }


    protected void findAllShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Shangpin> shangpinList = shangpinService.findAllShangpin();
        Map data = new HashMap();
        data.put("itemList", shangpinList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<Shangpin> shangpinList = shangpinService.findShangpinByName(name);
        Map data = new HashMap();
        data.put("itemList", shangpinList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Shangpin shangpinInfo = shangpinService.findShangpinById(id);
        Result result = Result.ok(shangpinInfo);
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinByGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String group = req.getParameter("group");
        List<Shangpin> shangpinList = shangpinService.findShangpinByGroup(group);
        Map data = new HashMap();
        data.put("itemList", shangpinList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void updateShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Shangpin shangpin = WebUtil.readJson(req,Shangpin.class);
        int rows = shangpinService.updateShangpin(shangpin);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    protected void deleteShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = shangpinService.deleteShangpinById(id);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }
}
