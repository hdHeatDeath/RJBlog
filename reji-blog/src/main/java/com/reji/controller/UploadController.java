package com.reji.controller;

import com.reji.domain.ResponseResult;
import com.reji.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public Object uploadImg(MultipartFile img){
        try {
            return uploadService.uploadImg(img);
        }catch (Exception e){
            System.out.println(e);
        }
        return ResponseResult.errorResult(000, "出现了某些错误");
    }
}
