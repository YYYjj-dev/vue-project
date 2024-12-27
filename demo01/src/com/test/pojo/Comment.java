package com.test.pojo;


public class Comment {

  private Integer id;
  private Integer userId;
  private Integer shangpinId;
  private Integer newsId;
  private String content;
  private String date;
  private Integer caseId;

  public Integer getCaseId() {
    return caseId;
  }

  public void setCaseId(Integer caseId) {
    this.caseId = caseId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public Integer getShangpinId() {
    return shangpinId;
  }

  public void setShangpinId(Integer shangpinId) {
    this.shangpinId = shangpinId;
  }


  public Integer getNewsId() {
    return newsId;
  }

  public void setNewsId(Integer newsId) {
    this.newsId = newsId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

}
