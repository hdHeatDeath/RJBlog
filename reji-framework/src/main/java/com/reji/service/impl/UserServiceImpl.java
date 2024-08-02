package com.reji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.User;
import com.reji.domain.vo.UserInfoVo;
import com.reji.enums.AppHttpCodeEnum;
import com.reji.handler.exception.SystemException;
import com.reji.service.UserService;
import com.reji.mapper.UserMapper;
import com.reji.utils.BeanCopyUtils;
import com.reji.utils.RedisCache;
import com.reji.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author 89755
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-02-15 16:33:43
*/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult userInfo() {
        UserInfoVo userInfoVo;
        // 查询当前用户id
        Long userId = SecurityUtils.getUserId();
        // 从缓存中获取数据
        Map<String, Object> userCache = redisCache.getCacheMap(userId.toString());
        if(userCache.isEmpty()) {
            // 根据用户id查询用户信息
            User user = getById(userId);
            // 将user转换成Map
            Map<String, User> userMap = new HashMap<>();
            userMap.put(user.getId().toString(), user);
            // 存入Redis
            redisCache.setCacheMap("user:userInfo", userMap);
            userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        } else {
            // 封装成UserInfoVo
            userInfoVo = BeanCopyUtils.copyBean(userCache, UserInfoVo.class);
        }

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        // 更新数据库
        updateById(user);
        // 删除缓存
        redisCache.deleteObject(user.getId().toString());

        return ResponseResult.okResult();
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {

        // 对数据进行判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        // 对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userEmailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 存入数据库
        save(user);

        // 将user转换成Map
        Map<String, User> userMap = new HashMap<>();
        userMap.put(user.getId().toString(), user);

        // 存入Redis
        redisCache.setCacheMap("user:userInfo", userMap);

        return ResponseResult.okResult();
    }

    private boolean userEmailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);

        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);

        return count(queryWrapper) > 0;
    }
}




