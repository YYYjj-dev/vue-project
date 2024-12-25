package com.example.controller;


import com.example.common.Result;
import com.example.entity.Comment;
import com.example.entity.Image;
import com.example.service.ImageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private ImageService imageService;

    @GetMapping("/images/{part}")
    public Result getImageByNewsId(@PathVariable("part") String part) {
        List<Image> images = imageService.getImageByPart(part);
        return Result.success(images);
    }
}
