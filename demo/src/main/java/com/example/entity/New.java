package com.example.entity;


import javax.persistence.*;

@Table(name = "news")
public class New {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "comment_id")
    private long commentId;
    @Column(name = "admin_id")
    private long adminId;
    @Column(name = "date")
    private String date;

    private New () {
        super();
    }

    public New(long id, String title, String content, long commentId, long adminId, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.commentId = commentId;
        this.adminId = adminId;
        this.date = date;
    }
}
