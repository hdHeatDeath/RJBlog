package com.reji.service;

import com.reji.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}
