package com.test.test;

import org.apache.commons.fileupload2.core.DiskFileItemFactory;

public class a {
    public static void main(String[] args) {
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();
        System.out.println(diskFileItemFactory);
    }
}
