package com.test.common;

public enum Group {
    ELDER(901,"老人"),
    CHILD(902,"儿童"),
    ADULT(903,"成人"),
    TEEN(904,"青少年");
    private Integer code;
    private String groupName;
    private Group(Integer code, String groupName) {
        this.code = code;
        this.groupName = groupName;
    }
}
