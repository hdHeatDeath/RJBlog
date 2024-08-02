package com.reji.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.User;
import com.reji.enums.AppHttpCodeEnum;
import com.reji.handler.exception.SystemException;
import com.reji.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService ;

    @PostMapping ("/login")
    public ResponseResult login(@RequestBody  User user){
        if(!StringUtils.hasText(user.getUserName())) {
            // 提示必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
