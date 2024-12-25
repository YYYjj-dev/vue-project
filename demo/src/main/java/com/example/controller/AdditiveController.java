package com.example.controller;


import com.example.common.Result;
import com.example.entity.Additive;
import com.example.service.AdditiveService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/additive")
public class AdditiveController {

    @Resource
    private AdditiveService additiveService;

    @RequestMapping("/getAdditives")
    public Result getAdditives(){
        List<Additive> additives = additiveService.getAdditives();
        return Result.success(additives);
    }
}
