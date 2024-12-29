package com.test.pojo;


public class Shangpin {

  private Integer id;
  private Integer storeId;
  private String group;
  private String type;
  private String name;
  private String description;
  private String standard;
  private Double price;
  private String score;
  private Integer num;
  private String imgpath;

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getImgpath() {
    return imgpath;
  }
  public void setImgpath(String imgpath) {
    this.imgpath = imgpath;
  }

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getStoreId() {
    return storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }


  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getStandard() {
    return standard;
  }

  public void setStandard(String standard) {
    this.standard = standard;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }




  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

}
