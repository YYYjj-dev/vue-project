package com.test.controller;


import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Merchant;
import com.test.service.MerchantService;
import com.test.service.impl.MerchantServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")

@WebServlet("/merchant/*")
public class MerchantController extends BaseController{

    private MerchantService merchantService = new MerchantServiceImpl();

    /**
     *根据用户名返回对应的商家
     */
    protected void findMerchantByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        Merchant merchant = merchantService.findMerchantByUsername(username);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(null!=merchant){
            result=Result.ok(merchant);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *返回所有商家
     */
    protected void findAllMerchants(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Merchant> merchantList = merchantService.findAllMerchants();
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(null!=merchantList){
            result=Result.ok(merchantList);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据商家类型（type）返回商家列表
     */
    protected void findMerchantByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        List<Merchant> merchantList = merchantService.findMerchantByType(type);
        Result result = Result.build(null,ResultCodeEnum.NOT_FOUND);
        if(!merchantList.isEmpty()){
            result=Result.ok(merchantList);
        }
        WebUtil.writeJson(resp,result);
    }


    /**
     *添加商家，传入商家对象
     */
    protected void addMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Merchant merchant = ImgUtil.updateMerchant(req);
        int rows = merchantService.addMerchant(merchant);
        Result result = Result.build(null,ResultCodeEnum.ADDITION_FAILED);
        if(rows>0){
            result=Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *修改商家，传入商家对象
     */
    protected void updateMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Merchant merchant = ImgUtil.updateMerchant(req);
        int rows = merchantService.updateMerchant(merchant);
        Result result = Result.build(null,ResultCodeEnum.UPDATE_FAILED);
        if(rows>0){
            result=Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }


    /**
     *删除商家，传入商家id
     */
    protected void deleteMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        int rows = merchantService.deleteMerchant(id);
        Result result = Result.build(null,ResultCodeEnum.DELETION_FAILED);
        if(rows>0){
            result=Result.ok(rows);
        }
        WebUtil.writeJson(resp,result);
    }

    //根据商家id查找商品或订单均在shangpinController


}
