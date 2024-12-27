package com.test.controller;

import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.*;
import com.test.service.InfoService;
import com.test.service.impl.InfoServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")
@WebServlet("/info/*")
public class InfoController extends BaseController{
    private InfoService infoService = new InfoServiceImpl();

    //新闻相关

    protected void findAllNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<News> itemList = infoService.findAllNews();
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void findNewsById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        News newsInfo = infoService.findNewsById(id);
        Result result = Result.ok(newsInfo);
        WebUtil.writeJson(resp,result);
    }

    protected void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.DeleteNews(id);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    protected void addNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = ImgUtil.updateNews(req);
        int rows = infoService.addNews(news);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void updateNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = WebUtil.readJson(req,News.class);
        int rows = infoService.updateNews(news);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    protected void findNewsByTitle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        List<News> itemList = infoService.findNewsByTitle(title);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    /**
     *案例相关业务
     */
    protected void findAllCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cases> itemList = infoService.findAllCases();
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void findCasesById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Cases casesInfo = infoService.findCasesById(id);
        Result result = Result.ok(casesInfo);
        WebUtil.writeJson(resp,result);
    }

    protected void findCasesByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        List<Cases> itemList = infoService.findCasesByType(type);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void addCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cases cases = ImgUtil.updateCases(req, resp);
        int rows = infoService.addCases(cases);
        Result result = Result.build(null, ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }
    protected void updateCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cases cases = ImgUtil.updateCases(req, resp);
        int rows = infoService.updateCases(cases);
        Result result = Result.build(null, ResultCodeEnum.UPDATE_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void deleteCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.deleteCases(id);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }


    //法规相关

    protected void findAllRegular(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Regulation> itemList = infoService.findAllAllRegular();
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void findRegularById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Regulation regulation = infoService.findRegulationById(id);
        Result result = Result.ok(regulation);
        WebUtil.writeJson(resp,result);
    }

    protected void addRegular(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Regulation regulation = WebUtil.readJson(req,Regulation.class);
        int rows = infoService.addRegular(regulation);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    protected void updateRegular(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Regulation regulation = WebUtil.readJson(req,Regulation.class);
        int rows = infoService.updateRegular(regulation);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }

    protected void deleteRegular(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.deleteRegular(id);
        if(rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
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
