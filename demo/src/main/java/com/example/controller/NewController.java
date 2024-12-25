package com.example.controller;


import com.example.common.Result;
import com.example.entity.New;
import com.example.service.NewService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/new")
public class NewController {

    @Resource
    private NewService newService;

    @GetMapping
    public Result getNew(){
        List<New> newList = newService.getNew();
        return Result.success(newList);
    }

}
