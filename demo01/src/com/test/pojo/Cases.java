package com.test.pojo;


public class Cases {

  private long id;
  private String title;
  private String content;
  private long commentId;
  private String imgpath;
  private String grouptype;
  private String date;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }


  public String getImgpath() {
    return imgpath;
  }

  public void setImgpath(String imgpath) {
    this.imgpath = imgpath;
  }


  public String getGrouptype() {
    return grouptype;
  }

  public void setGrouptype(String grouptype) {
    this.grouptype = grouptype;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

}
