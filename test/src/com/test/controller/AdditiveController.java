package com.test.controller;

import com.test.common.Result;
import com.test.pojo.Additive;
import com.test.pojo.User;
import com.test.service.AdditiveService;
import com.test.service.impl.AdditiveServiceImpl;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/additive/*")
public class AdditiveController extends BaseController {
    private AdditiveService additiveService = new AdditiveServiceImpl();

    protected void findAllAdditives(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Additive> itemList = additiveService.findAllAdditive();
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void findAdditiveById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Additive additiveInfo = additiveService.findAdditiveById(id);
        Result result = Result.ok(additiveInfo);
        WebUtil.writeJson(resp,result);

    }
    protected void addAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Additive addAdditive = WebUtil.readJson(req,Additive.class);
        int rows = additiveService.addAdditive(addAdditive);
    }

    protected void findAdditiveByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<Additive> itemList = additiveService.findAdditiveByName(name);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void findAdditiveByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeName = req.getParameter("typeName");
        List<Additive> itemList = additiveService.findAdditiveByType(typeName);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }
}
