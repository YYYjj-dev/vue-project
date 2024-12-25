package com.example.controller;


import com.example.common.Result;
import com.example.entity.Regulation;
import com.example.service.RegulationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/regulation")
public class RegulationController {

    @Resource
    private RegulationService regulationService;

    @GetMapping
    public Result getRegulations(){
        List<Regulation> regulations = regulationService.getRegulations();
        return Result.success(regulations);
    }


}
