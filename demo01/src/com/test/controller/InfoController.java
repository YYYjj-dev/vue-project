package com.test.controller;

import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.*;
import com.test.service.InfoService;
import com.test.service.impl.InfoServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.JwtTokenUtils;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 该Servlet处理信息相关
 */
@SuppressWarnings("all")
@WebServlet("/info/*")
public class InfoController extends BaseController{
    private InfoService infoService = new InfoServiceImpl();

    //资讯相关

    /**
     *查找所有资讯，返回资讯集合，失败返回业务码404
     */
    protected void findAllNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<News> itemList = infoService.findAllNews();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(itemList.size()>0){
            result=Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *通过资讯id查找资讯，返回单个资讯，失败返回业务码404
     */
    protected void findNewsById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        News newsInfo = infoService.findNewsById(id);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(newsInfo!=null){
            result=Result.ok(newsInfo);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据资讯type查找资讯，返回资讯集合，失败返回业务码404
     */
    protected void findNewsByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        List<News> newsList = infoService.findNewsByType(type);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(newsList.size()>0){
            result=Result.ok(newsList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据资讯id删除资讯，传入管理员用户token，失败返回业务码402
     */
    protected void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user!=null&&user.getType().equals("admin")){
            Integer id = Integer.parseInt(req.getParameter("id"));
            int rows = infoService.DeleteNews(id);
            if (rows > 0) {
                result=Result.ok(rows);
            }else {

            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *增加单个资讯，传入单个资讯对象，失败返回业务码401
     */
    protected void addNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = ImgUtil.updateNews(req);
        int rows = infoService.addNews(news);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *修改资讯，传入单个资讯对象，失败返回业务码403
     */
    protected void updateNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = ImgUtil.updateNews(req);
        int rows = infoService.updateNews(news);
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *通过资讯title，模糊查询资讯，失败返回404
     */
    protected void findNewsByTitle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        List<News> itemList = infoService.findNewsByTitle(title);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(itemList.size()>0){
            result=Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *多条件查询咨询，可选titie，type
     */
    protected void findNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", req.getParameter("title"));
        queryParams.put("type", req.getParameter("type"));
        List<News> newsList = infoService.findNews(queryParams);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(newsList.size()>0){
            result=Result.ok(newsList);
        }
        WebUtil.writeJson(resp,result);
    }


     //案例相关业务

    /**
     *查找所有案例，返回案例集合，失败返回业务码404
     */
    protected void findAllCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cases> itemList = infoService.findAllCases();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(itemList.size()>0){
            result=Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据id查找案例，返回单个案例对象，失败返回业务码404
     */
    protected void findCasesById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Cases casesInfo = infoService.findCasesById(id);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(casesInfo!=null){
            result=Result.ok(casesInfo);
        }
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

    /**
     *多条件查询案例，可选title，grouptype
     */
    protected void findCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", req.getParameter("title"));
        queryParams.put("grouptype", req.getParameter("grouptype"));
        List<Cases> casesList = infoService.findCases(queryParams);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(casesList.size()>0){
            result=Result.ok(casesList);
        }
        WebUtil.writeJson(resp,result);
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

    protected void findRegularByTitle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        List<Regulation> regularList = infoService.findRegularByTitle(title);
        Result result = Result.ok(regularList);
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
     * 返回评论列表，属性有id,userId,content,date,img,用户种类:type
     */
    protected void showComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CommentList> commentList=null;
        Integer commentId = Integer.valueOf(req.getParameter("commentId"));
        String commentType = req.getParameter("commentType");
        if(commentType.equals("news")){
            commentList = infoService.findNewsCommentById(commentId);
        }else if(commentType.equals("shangpin")){
            commentList = infoService.findShangpinCommentById(commentId);
        }else if(commentType.equals("case")){
            commentList = infoService.findCasesCommentById(commentId);
        }
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(commentList!=null || commentList.size()!=0){
            result = Result.ok(commentList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 添加评论, 传入用户token ,一个封装的评论对象:userId, (shangpinId,newsId,caseId)其中之一，content,date
     */
    protected void addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        Comment comment = tokenInfo.getCommentInfo();
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        int rows = 0;
        if(user!=null){
            comment.setUserId(user.getId());
            String content = comment.getContent();
            Integer sid = comment.getShangpinId();
            Integer nid = comment.getNewsId();
            Integer cid = comment.getCaseId();
            String date = comment.getDate();
           if(sid != null){
               rows = infoService.addShangpinComment(comment);
           }else if(nid != null){
               rows = infoService.addNewsComment(comment);
           }else if(cid != null){
               rows = infoService.addCasesComment(comment);
           }

            if(rows>0){
                result = Result.ok(rows);
            }
        }else {
            result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *删除评论，传入用户token，评论对象（只包含id即可）
     */
    protected void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Comment comment = tokenInfo.getCommentInfo();
        Integer id = comment.getId();
        Result result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        int rows = 0;
        if(user.getId()== infoService.getCommentById(id).getUserId()||user.getType().equals("admin")){
            rows = infoService.deleteComment(id);
            if(rows>0){
                result = Result.ok(rows);
            }
        }else{
            result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *按用户id查找评论，传入token
     */
    protected void findCommentByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if(token==null){
            token = WebUtil.readJson(req,String.class);
        }
        User user = JwtTokenUtils.checkToken(token);
        Integer id = user.getId();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        List<Comment> commentList=infoService.findCommentByUid(id);
        if(commentList!=null || commentList.size()!=0){
            result = Result.ok(commentList);
        }
        WebUtil.writeJson(resp,result);
    }


    //回复相关


    /**
     *添加回复，传入用户token,reply对象
     */
    protected void addReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Reply reply = tokenInfo.getReplyInfo();
        Result result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        int rows = 0;
        if(user!=null&&user.getUsername().equals(reply.getUsername())){
            rows = infoService.addReply(reply);
            if(rows>0){
                result = Result.ok(rows);
            }else {
                result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
            }
        }
        WebUtil.writeJson(resp,result);
    }


    protected void showReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reply> replyList= infoService.showReply();
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(replyList!=null || replyList.size()!=0){
            result = Result.ok(replyList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *删除回复，传入用户token，reply对象(只包含id)
     */
    protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Integer id = tokenInfo.getReplyInfo().getId();
        Result result = Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        int rows = 0;
        if(user!=null&&user.getUsername().equals(infoService.getUsernameByRid(id))){
            rows = infoService.deleteReply(id);
            if(rows>0){
                result = Result.ok(rows);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据uid查找用户回复，传入用户token
     */
    protected void findReplyByUid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if(token==null){
            token = WebUtil.readJson(req,String.class);
        }
        User user = JwtTokenUtils.checkToken(token);
        List<Reply> replyList = infoService.findReplyByUid(user.getId());
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(replyList!=null || replyList.size()!=0){
            result = Result.ok(replyList);
        }
        WebUtil.writeJson(resp,result);
    }
    //反馈相关

    /**
     *添加反馈
     */
    protected void addFeedback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Feedback feedback = WebUtil.readJson(req,Feedback.class);
        int rows = infoService.addFeedback(feedback);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findFeedbackById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        List<Feedback> feedbackList = infoService.findFeedbackById(id);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if (feedbackList != null) {
            result = Result.ok(feedbackList);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void deleteFeedback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.deleteFeedback(id);
        Result result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    //轮播图相关
    protected void addCarousel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Image image = ImgUtil.updateImage(req);
        int rows = infoService.addCarousel(image);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void deleteCarousel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = infoService.deleteCarousel(id);
        Result result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }
}
