package com.example.service;


import com.example.dao.ImageDao;
import com.example.entity.Comment;
import com.example.entity.Image;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Resource
    private ImageDao imageDao;

    public List<Image> getImageByPart(String part) {
        return imageDao.getImageByPart(part);
    }
}
