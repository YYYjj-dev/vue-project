package com.test.pojo;


public class Order {

  private long id;
  private String date;
  private long xiangqingId;
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public long getXiangqingId() {
    return xiangqingId;
  }

  public void setXiangqingId(long xiangqingId) {
    this.xiangqingId = xiangqingId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
