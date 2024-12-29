package com.test.pojo;


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
