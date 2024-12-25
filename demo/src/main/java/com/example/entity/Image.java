package com.example.entity;


import javax.persistence.Table;

@Table(name = "image")
public class Image {


    private Integer id;
    private String part;
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image(Integer id, String part, String path) {
        this.id = id;
        this.part = part;
        this.path = path;
    }

    public Image() {
        super();
    }
}
