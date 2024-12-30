package com.test.controller;


import com.test.common.Result;
import com.test.common.ResultCodeEnum;
import com.test.pojo.Merchant;
import com.test.pojo.News;
import com.test.pojo.TokenInfo;
import com.test.pojo.User;
import com.test.service.MerchantService;
import com.test.service.impl.MerchantServiceImpl;
import com.test.util.ImgUtil;
import com.test.util.JwtTokenUtils;
import com.test.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")

@WebServlet("/merchant/*")
public class MerchantController extends BaseController{

    private MerchantService merchantService = new MerchantServiceImpl();

    /**
     *根据用户名返回对应的商家,传入商家用户token
     */
    protected void findMerchantByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        if(token != null) {
            token = WebUtil.readJson(req,String.class);
        }
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null, ResultCodeEnum.USERTYPE_ERROR);
        if(user.getType().equals("merchant")||user.getType().equals("admin")){
            String username = user.getUsername();
            Merchant merchant = merchantService.findMerchantByUsername(username);
            if(null!=merchant){
                result = Result.ok(merchant);
            }else{
                result = Result.build(null,ResultCodeEnum.NOT_FOUND);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *根据id返回商家
     */
    protected void findMerchantById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Merchant merchantInfo = merchantService.findMerchantById(id);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(null!=merchantInfo){
            result = Result.ok(merchantInfo);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void findMerchantBySid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("sid"));
        Merchant merchantInfo = merchantService.findMerchantBySid(id);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(null!=merchantInfo){
            result = Result.ok(merchantInfo);
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
     *添加商家，传入商家用户token,商家对象(除logo外所有属性，添加时不带logo）
     */
    protected void addMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user!=null&&user.getType().equals("merchant")||user.getType().equals("admin")){
            Merchant merchant = tokenInfo.getMerchantInfo();
            int rows = merchantService.addMerchant(merchant);
            if(rows>0){
                result=Result.ok(rows);
            }else{
                result=Result.build(null,ResultCodeEnum.ADDITION_FAILED);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *修改商家，传入商家对象
     */
    protected void updateMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Merchant merchant = ImgUtil.updateMerchant(req);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
            if(null!=merchant){
                int rows = merchantService.updateMerchant(merchant);
                if(rows>0){
                    result=Result.ok(rows);
                }else {
                    result=Result.build(null,ResultCodeEnum.UPDATE_FAILED);
                }
            }
        WebUtil.writeJson(resp,result);
    }

    /**
     *删除商家，传入商家对象（只包含id即可）和商家用户token
     */
    protected void deleteMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TokenInfo tokenInfo = WebUtil.readTokenJson(req);
        String token = tokenInfo.getToken();
        User user = JwtTokenUtils.checkToken(token);
        Result result = Result.build(null,ResultCodeEnum.USERTYPE_ERROR);
        if(user!=null&&(user.getType().equals("merchant")||user.getType().equals("admin"))){
            Merchant merchant = merchantService.findMerchantByUsername(user.getUsername());
            if(null!=merchant){
                int id = Integer.valueOf(req.getParameter("id"));
                int rows = merchantService.deleteMerchant(id);
                if(rows>0){
                    result=Result.ok(rows);
                }else {
                    result=Result.build(null,ResultCodeEnum.DELETION_FAILED);
                }
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     *多条件查询merchant，可传入name，type
     */
    protected void findMerchant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", req.getParameter("name"));
        queryParams.put("type", req.getParameter("type"));
        List<Merchant> merchantList = merchantService.findMerchant(queryParams);
        Result result = Result.build(null, ResultCodeEnum.NOT_FOUND);
        if(null!=merchantList){
            result=Result.ok(merchantList);
        }
        WebUtil.writeJson(resp,result);
    }

    //根据商家id查找商品或订单均在shangpinController


}
