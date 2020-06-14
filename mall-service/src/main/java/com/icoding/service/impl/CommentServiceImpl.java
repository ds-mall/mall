package com.icoding.service.impl;

import com.icoding.mapper.ItemsCommentsMapper;
import com.icoding.pojo.ItemsComments;
import com.icoding.service.CommentService;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.UserCenterCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  ItemsCommentsMapper itemsCommentsMapper;

  @Override
  public int saveComment(List<ItemsComments> commentsList) {
    return itemsCommentsMapper.batchInsertComments(commentsList);
  }

  @Override
  public PagedGridResult<UserCenterCommentVO> queryCommentsByUserId(String userId, Integer page, Integer pageSize) {
    if(page == null) {
      page = 1;
    }
    if(pageSize == null) {
      pageSize = 20;
    }

    int start = (page - 1) * pageSize;
    int end = pageSize * page;

    int totalCounts = itemsCommentsMapper.getUserCommentsCounts(userId);
    int totalPages = totalCounts % pageSize;

    List<UserCenterCommentVO> commentVOList = itemsCommentsMapper.getUserComments(userId, start, end);
    PagedGridResult<UserCenterCommentVO> grid = new PagedGridResult<>();
    grid.setPage(page);
    grid.setTotal(totalPages);
    grid.setRecords(totalCounts);
    grid.setRows(commentVOList);

    return grid;
  }
}
