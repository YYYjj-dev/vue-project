package com.test.controller;

import com.test.common.Result;
import com.test.pojo.Cases;
import com.test.pojo.News;
import com.test.service.InfoService;
import com.test.service.UserService;
import com.test.service.impl.InfoServiceImpl;
import com.test.service.impl.UserServiceImpl;
import com.test.util.WebUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/info/*")
public class InfoController extends BaseController{
    private InfoService infoService = new InfoServiceImpl();

    protected void findAllNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<News> itemList = infoService.findAllNews();
        Map data = new HashMap();
        data.put("itemList", itemList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }
    protected void findNewsById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        News newsInfo = infoService.findNewsById(id);
        Result result = Result.ok(newsInfo);
        WebUtil.writeJson(resp,result);

    }
    protected void findAllCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cases> itemList = infoService.findAllCases();
        Map data = new HashMap();
        data.put("itemList", itemList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void findCasesById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Cases casesInfo = infoService.findCasesById(id);
        Result result = Result.ok(casesInfo);
        WebUtil.writeJson(resp,result);
    }
}
