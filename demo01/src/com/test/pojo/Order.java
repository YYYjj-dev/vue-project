package com.test.pojo;


public class Order {

  private Integer id;
  private String date;
  private Integer userId;
  private Double sub;
  private Integer quantity;
  private Integer shangpinId;
  private Shangpin status;

  public Shangpin getStatus() {
    return status;
  }

  public void setStatus(Shangpin status) {
    this.status = status;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getUserId() {
    return userId;
  }

  public Double getSub() {
    return sub;
  }

  public void setSub(Double sub) {
    this.sub = sub;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getShangpinId() {
    return shangpinId;
  }

  public void setShangpinId(Integer shangpinId) {
    this.shangpinId = shangpinId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

}
