package com.reji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-01-23 15:03:58
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

