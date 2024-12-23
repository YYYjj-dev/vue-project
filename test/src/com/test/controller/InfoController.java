package com.test.controller;

import com.test.common.Result;
import com.test.pojo.*;
import com.test.service.InfoService;
import com.test.service.impl.InfoServiceImpl;
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

    //新闻相关

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

    protected void DeleteNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.DeleteNews(id);
        Result result = Result.ok(rows);
        WebUtil.writeJson(resp,result);
    }

    protected void addNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = WebUtil.readJson(req,News.class);
        int rows = infoService.addNews(news.getTitle(),news.getContent(),news.getDate(),news.getAdminId());
    }



    //案例相关

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



    //法规相关

    protected void findAllRegular(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Regulation> itemList = infoService.findAllAllRegular();
        Map data = new HashMap();
        data.put("itemList", itemList);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void findRegularById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Regulation regulation = infoService.findRegulationById(id);
        Result result = Result.ok(regulation);
        WebUtil.writeJson(resp,result);
    }


    //评论相关

    /**
     * 返回评论
     */
    protected void showComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Comment> commentList=null;
        Integer commentId = Integer.valueOf(req.getParameter("commentId"));
        String commentType = req.getParameter("commentType");
        if(commentType.equals("1")){
            commentList = infoService.findNewsCommentById(commentId);
        }else if(commentType.equals("2")){
            commentList = infoService.findShangpinCommentById(commentId);
        }
        Result result = Result.ok(commentList);
        WebUtil.writeJson(resp,result);
    }
    /**
      添加评论
     */
    protected void addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer uid = Integer.parseInt(req.getParameter("uid"));
        String content = req.getParameter("content");
        String commentType = req.getParameter("commentType");
        Integer cid = Integer.valueOf(req.getParameter("cid"));
        String date = req.getParameter("date");
        int rows = infoService.addComment(uid,cid,content,commentType,date);
    }

}
