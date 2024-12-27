package com.test.pojo;


public class Store {

  private long id;
  private long shangjiaId;
  private String name;
  private String description;
  private String imgpath;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getShangjiaId() {
    return shangjiaId;
  }

  public void setShangjiaId(long shangjiaId) {
    this.shangjiaId = shangjiaId;
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


  public String getImgpath() {
    return imgpath;
  }

  public void setImgpath(String imgpath) {
    this.imgpath = imgpath;
  }

}
