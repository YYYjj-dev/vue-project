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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
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
        if(!itemList.isEmpty()){
            result = Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *按id查找添加剂，失败返回业务码404
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

    /**
     *按name模糊查找添加剂，失败返回业务码404
     */
    protected void findAdditiveByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<Additive> itemList = additiveService.findAdditiveByName(name);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(!itemList.isEmpty()){
            result = Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *按条件查找添加剂，可传入name,type,nature
     */
    protected void findAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", req.getParameter("name"));
        queryParams.put("typename", req.getParameter("type"));
        queryParams.put("nature", req.getParameter("nature"));
        List<Additive> additiveList = additiveService.findAdditive(queryParams);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (additiveList != null) {
            result = Result.ok(additiveList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *增加添加剂，传入添加剂对象，失败返回业务码401
     */
    protected void addAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Additive additive = ImgUtil.updateAdditive(req);
        int rows = additiveService.addAdditive(additive);
        Result result = Result.build(null, ResultCodeEnum.ADDITION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据种类typename查找添加剂,失败返回业务码404
     */
    protected void findAdditiveByType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeName = req.getParameter("typename");
        List<Additive> itemList = additiveService.findAdditiveByType(typeName);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(!itemList.isEmpty()){
            result = Result.ok(itemList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *按nature查找添加剂，传入nature，属性为"天然"或"人工"失败返回404
     */
    protected void findAdditiveByNature(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nature = req.getParameter("nature");
        List<Additive> additiveInfo = additiveService.findAdditiveByNature(nature);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if (additiveInfo != null) {
            result = Result.ok(additiveInfo);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *修改添加剂，传入添加剂对象，失败返回业务码403
     */
    protected void updateAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Additive additive = ImgUtil.updateAdditive(req);
        int rows = additiveService.updateAdditive(additive);
        Result result = Result.build(null, ResultCodeEnum.UPDATE_USER_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     *根据id删除添加剂，失败返回业务码402
     */
    protected void deleteAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        int rows = additiveService.deleteAdditive(id);
        Result result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if (rows > 0) {
            result = Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void getAdditiveNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer num = additiveService.getAdditiveNum();
        Result result = Result.ok(num);
        WebUtil.writeJson(resp,result);
    }

}
