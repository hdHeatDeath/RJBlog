package com.reji.service;

import com.reji.domain.ResponseResult;
import com.reji.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 89755
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-02-15 16:33:43
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
