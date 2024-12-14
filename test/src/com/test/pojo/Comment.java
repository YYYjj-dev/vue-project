package com.test.pojo;


public class Comment {

  private long id;
  private long userId;
  private long shangpinId;
  private long newsId;
  private String content;
  private String date;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getShangpinId() {
    return shangpinId;
  }

  public void setShangpinId(long shangpinId) {
    this.shangpinId = shangpinId;
  }


  public long getNewsId() {
    return newsId;
  }

  public void setNewsId(long newsId) {
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
