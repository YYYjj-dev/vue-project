package com.example.entity;


import javax.persistence.Table;

@Table(name = "regulation")
public class Regulation {



    private long id;
    private String title;
    private String date;
    private String content;
    private long adminId;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public Regulation(long id, String title, String date, String content, long adminId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.adminId = adminId;
    }

    public Regulation() {
        super();
    }

}
