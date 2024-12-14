package com.test.pojo;


public class Orderdetail {

  private long id;
  private long orderId;
  private long shangpinId;
  private long quantity;
  private double sub;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public long getShangpinId() {
    return shangpinId;
  }

  public void setShangpinId(long shangpinId) {
    this.shangpinId = shangpinId;
  }


  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }


  public double getSub() {
    return sub;
  }

  public void setSub(double sub) {
    this.sub = sub;
  }

}
