package com.test.common;

public enum Type {
    TYPE01(001,"type01"),
    TYPE02(002,"type02"),
    TYPE03(003,"type03");


    public int typeNum;
    private String typeName;
    private Type(int typeNum, String typeName) {
        this.typeNum = typeNum;
        this.typeName = typeName;
    }
}
