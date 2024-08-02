package com.reji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reji.domain.ResponseResult;
import com.reji.domain.entity.Comment;

/**
* @author 89755
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2024-03-04 13:40:07
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
