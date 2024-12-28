package com.test.controller;

import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Additive;
import com.test.service.AdditiveService;
import com.test.service.impl.AdditiveServiceImpl;
import com.test.util.DataUtils;
import com.test.util.ImgUtil;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/additive/*")
public class AdditiveController extends BaseController {
    private AdditiveService additiveService = new AdditiveServiceImpl();

    /**
     * 返回所有添加剂
     * 失败返回业务码404
     */
    protected void findAllAdditives(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Additive> itemList = additiveService.findAllAdditive();
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(itemList.size()>0){
            result = Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *按ID查找添加剂，失败返回业务码404
     */
    protected void findAdditiveById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        Additive additiveInfo = additiveService.findAdditiveById(id);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (additiveInfo != null) {
            result = Result.ok(additiveInfo);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findAdditiveByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<Additive> itemList = additiveService.findAdditiveByName(name);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void addAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Additive additive = ImgUtil.updateAdditive(req);
        int rows = additiveService.addAdditive(additive);
        Result result = Result.build(null, ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }


    protected void findAdditiveByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeName = req.getParameter("typename");
        List<Additive> itemList = additiveService.findAdditiveByType(typeName);
        Result result = Result.ok(itemList);
        WebUtil.writeJson(resp,result);
    }

    protected void updateAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Additive additive = ImgUtil.updateAdditive(req);
        int rows = additiveService.updateAdditive(additive);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp, result);
        }
    }

    protected void deleteAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = additiveService.deleteAdditive(id);
        if (rows > 0) {
            Result result = Result.ok(rows);
            WebUtil.writeJson(resp,result);
        }
    }
}
