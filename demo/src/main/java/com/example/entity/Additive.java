package com.example.entity;


import javax.persistence.Table;

@Table(name = "additive")
public class Additive {


    private long id;
    private String typename;
    private String name;
    private String description;
    private String usestandardInternal;
    private String usestandardInternational;
    private String analysis;
    private Integer adminId;
    private String imgpath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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

    public String getUsestandardInternal() {
        return usestandardInternal;
    }

    public void setUsestandardInternal(String usestandardInternal) {
        this.usestandardInternal = usestandardInternal;
    }

    public String getUsestandardInternational() {
        return usestandardInternational;
    }

    public void setUsestandardInternational(String usestandardInternational) {
        this.usestandardInternational = usestandardInternational;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Additive(long id, String typename, String name, String description, String userstandardInternal, String userstandardInternational, String analysis, Integer adminId, String imgpath) {
        this.id = id;
        this.typename = typename;
        this.name = name;
        this.description = description;
        this.usestandardInternal = userstandardInternal;
        this.usestandardInternational = userstandardInternational;
        this.analysis = analysis;
        this.adminId = adminId;
        this.imgpath = imgpath;
    }

    public Additive() {
        super();
    }
}
