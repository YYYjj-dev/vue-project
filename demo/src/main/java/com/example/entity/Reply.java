package com.example.entity;


import javax.persistence.*;

@Table(name = "reply")
public class Reply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "comment_id")
    private long commentId;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Reply(long id, long commentId, String content, String date) {
        this.id = id;
        this.commentId = commentId;
        this.content = content;
        this.date = date;
    }

    public Reply() {
        super();
    }
}
