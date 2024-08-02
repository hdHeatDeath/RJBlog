package com.reji.runner;

import com.reji.domain.entity.User;
import com.reji.mapper.UserMapper;
import com.reji.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserInfoRunner implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查询所有用户
        List<User> users = userMapper.selectList(null);
        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(user -> user.getId().toString(),
                        user -> user));

        // 存入Redis
        redisCache.setCacheMap("user:userInfo", userMap);
        System.out.println("成功存入");
    }
}
