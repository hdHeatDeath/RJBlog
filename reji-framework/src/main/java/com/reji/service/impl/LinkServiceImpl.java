package com.reji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.reji.Constants.SystemConstants;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.Link;
import com.reji.domain.vo.LinkVo;
import com.reji.mapper.LinkMapper;
import com.reji.service.LinkService;
import com.reji.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 89755
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2024-02-10 16:51:07
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);

        // 转换为VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        //封装返回
        return ResponseResult.okResult(linkVos);
    }
}




