package com.test.pojo;


import java.util.List;

/**
 * 处理前端传回的带有token的数据
 */
public class TokenInfo {
    private String token;
    private Comment commentInfo;
    private Merchant merchantInfo;
    private Reply replyInfo;
    private Order orderInfo;
    private Score scoreInfo;
    private Feedback feedbackInfo;
    private Shangpin shangpinInfo;
    private Integer id;
    private List<Integer> oidList;

    public List<Integer> getOidList() {
        return oidList;
    }

    public void setOidList(List<Integer> oidList) {
        this.oidList = oidList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Feedback getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(Feedback feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public Shangpin getShangpinInfo() {
        return shangpinInfo;
    }

    public void setShangpinInfo(Shangpin shangpinInfo) {
        this.shangpinInfo = shangpinInfo;
    }

    public Score getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(Score scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

    public Reply getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(Reply replyInfo) {
        this.replyInfo = replyInfo;
    }

    public Order getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Order orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Comment getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(Comment commentInfo) {
        this.commentInfo = commentInfo;
    }

    public Merchant getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(Merchant merchantInfo) {
        this.merchantInfo = merchantInfo;
    }
}
