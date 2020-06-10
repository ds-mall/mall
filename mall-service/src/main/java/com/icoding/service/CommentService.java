package com.icoding.service;

import com.icoding.pojo.ItemsComments;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.UserCenterCommentVO;
import com.icoding.vo.UserCenterOrderVO;

import java.util.List;

public interface CommentService {
  /**
   * 批量插入评论
   * @param commentsList
   */
  int saveComment(List<ItemsComments> commentsList);

  /**
   * 查询用户评论列表
   * @param userId
   * @param page
   * @param pageSize
   * @return
   */
  PagedGridResult<UserCenterCommentVO> queryCommentsByUserId(String userId, Integer page, Integer pageSize);
}
