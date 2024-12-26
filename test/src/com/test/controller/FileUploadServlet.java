package com.test.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet("/image")
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
            DiskFileItemFactory作用；
                DiskFileItemFactory负责创建FileItem对象，并决定这些对象在内存中缓存的大小和临时文件存储的路径。
                这个类通常与ServletFileUpload类一起使用，
                ServletFileUpload类会使用DiskFileItemFactory来创建FileItem对象。
         */
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();

        /*
            ServletFileUpload作用：
                ServletFileUpload是Apache Commons FileUpload库中的一个类，用于处理文件上传请求。
                它能够解析HTTP请求中的multipart/form-data数据，提取出上传的文件及其他相关信息。
                这个类可以处理多个文件上传请求，并且提供了一些便捷的方法来访问上传的文件内容和相关信息。
                ServletFileUpload类负责解析上传的文件，将文件内容保存到磁盘或者内存中，并提供给开发者访问。
         */
        JakartaServletFileUpload fileUpload = new JakartaServletFileUpload(diskFileItemFactory);

        // 判断是不是multipart/form-data数据
        if (JakartaServletFileUpload.isMultipartContent(request)){

            //解析出request中的数据，自动放入到list中
            List list = fileUpload.parseRequest(request);

            for (Object obj : list) {
                FileItem fileItem = (FileItem) obj;
                // 判断是不是表单文本数据，取反就是文件数据
                if (!fileItem.isFormField()){
                    // 创建目录 用来存放传递来的文件
                    // 根据获取到的时间创建多级目录
                    //   WebUtil.getDate() ---> 24/04/15
                    String uploadPath = request.getServletContext().getRealPath("/upload") +"/";
                    System.out.println(uploadPath);
                    File uploadPathDir = new File(uploadPath);
                    if (!uploadPathDir.exists()){
                        uploadPathDir.mkdirs();
                    }
                    // 避免文件重名
                    String fileDiskPath = uploadPath+ UUID.randomUUID()+"_" +fileItem.getName();
                    File uploadFileDisk = new File(fileDiskPath);
                    //将网络文件写入磁盘
                    fileItem.write(uploadFileDisk.toPath());
                }

            }
        }
    }
}
