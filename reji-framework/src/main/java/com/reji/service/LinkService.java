package com.reji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.Link;

/**
* @author 89755
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2024-02-10 16:51:07
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

}
