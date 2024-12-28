package com.test.controller;


import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Shangpin;
import com.test.service.ShangpinService;
import com.test.service.impl.ShangpinServiceImpl;
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

@WebServlet("/shangpin/*")
public class ShangpinController extends BaseController{

    private ShangpinService shangpinService = new ShangpinServiceImpl();

    /**
     *添加商品，传入完整的商品对象，失败返回业务码401
     */
    public void addShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Shangpin shangpin = ImgUtil.updateShangpin(req);
        int rows = shangpinService.addShangpin(shangpin);
        Result result = Result.build(null, ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }


    /**
     *查找所有商品，失败返回业务码404
     */
    protected void findAllShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Shangpin> shangpinList = shangpinService.findAllShangpin();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if (!shangpinList.isEmpty()) {
            result = Result.ok(shangpinList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<Shangpin> shangpinList = shangpinService.findShangpinByName(name);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (!shangpinList.isEmpty()) {
            result = Result.ok(shangpinList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Shangpin shangpinInfo = shangpinService.findShangpinById(id);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (shangpinInfo != null) {
            result = Result.ok(shangpinInfo);
        }
        WebUtil.writeJson(resp,result);
    }


    protected void findShangpinByGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String group = req.getParameter("group");
        List<Shangpin> shangpinList = shangpinService.findShangpinByGroup(group);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (!shangpinList.isEmpty()) {
            result = Result.ok(shangpinList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findShangpinByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        List<Shangpin> shangpinList = shangpinService.findShangpinByType(type);
        Result result = Result.ok(shangpinList);
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据商家id查找商品，失败返回业务码404
     */
    protected void findShangpinByMid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer mid = Integer.valueOf(req.getParameter("mid"));
        List<Shangpin> shangpinList = shangpinService.findShangpinByMid(mid);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (!shangpinList.isEmpty()) {
            result = Result.ok(shangpinList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void updateShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Shangpin shangpin = ImgUtil.updateShangpin(req);
        int rows = shangpinService.updateShangpin(shangpin);
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void deleteShangpinById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = shangpinService.deleteShangpinById(id);
        Result result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }
}
