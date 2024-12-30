package com.test.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.pojo.Comment;
import com.test.pojo.News;
import com.test.util.OrderUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UtilTest {


    @Test
    public void orderTest(){
        String s = OrderUtils.getOrderCode(1);
        System.out.println(s);
        Long oid = Long.valueOf(s.substring(15,30));
        System.out.println(oid);
    }
    @Test
    public void reflectTest() throws Exception{
        ReflectT<?> reflectT=new ReflectT<>("key", News.class);
        System.out.println(reflectT.getMyClass());
        String json = """
                {"key": "e","value": {"shangpinId": 1,"content": "l","date": "2024 - 12 - 30"}}
                """;
        reflectT.readJson(json, Comment.class);
        System.out.println(reflectT.getMyClass());

}


}
