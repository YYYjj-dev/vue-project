package com.test.controller;

import com.test.common.Result;
import com.test.pojo.Additive;
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
        Map data = new HashMap();
        data.put("itemList", itemList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

}
