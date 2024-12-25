package com.example.entity;


import javax.persistence.*;

@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "shanpin_id")
    private long shanpinId;
    @Column(name = "new_id")
    private long newId;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private String date;

    public Comment(long id, long userId, long shanpinId, long newId, String content, String date) {
        this.id = id;
        this.userId = userId;
        this.shanpinId = shanpinId;
        this.newId = newId;
        this.content = content;
        this.date = date;
    }

    public Comment() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShanpinId() {
        return shanpinId;
    }

    public void setShanpinId(long shanpinId) {
        this.shanpinId = shanpinId;
    }

    public long getNewId() {
        return newId;
    }

    public void setNewId(long newId) {
        this.newId = newId;
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
}
