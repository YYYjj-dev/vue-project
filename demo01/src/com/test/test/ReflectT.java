package com.test.test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReflectT<T>{

    ObjectMapper objectMapper=new ObjectMapper();
    String key;
    T value;

    public ReflectT(String key, Class<?> clazz) throws Exception {
        this.key=key;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        this.value = (T)obj;
    }

    public ReflectT()  {}

    public ReflectT(ObjectMapper objectMapper, String key, T value) {
        this.objectMapper = objectMapper;
        this.key = key;
        this.value = value;
    }

    public Class getMyClass(){
        return this.getClass();
    }

    public  ReflectT<T> readJson(String json,Class clazz) throws Exception {
        ReflectT<T> t = new ReflectT<>(null,clazz);
        ReflectT<T> info = (ReflectT<T>) objectMapper.readValue(json,t.getMyClass());
        return t;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
