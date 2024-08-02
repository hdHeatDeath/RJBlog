package com.reji.service;

import com.reji.domain.ResponseResult;
import com.reji.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
