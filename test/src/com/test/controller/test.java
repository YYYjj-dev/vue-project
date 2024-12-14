package com.test.controller;

import com.test.common.Result;
import com.test.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class test extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String test = "test";
        Result result = Result.ok(test);
        WebUtil.writeJson(resp,result);
    }

}
