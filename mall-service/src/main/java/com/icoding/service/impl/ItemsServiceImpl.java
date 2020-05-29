package com.icoding.service.impl;

import com.icoding.enums.CommentLevel;
import com.icoding.mapper.*;
import com.icoding.pojo.Items;
import com.icoding.pojo.ItemsImg;
import com.icoding.pojo.ItemsParam;
import com.icoding.pojo.ItemsSpec;
import com.icoding.service.ItemsService;
import com.icoding.utils.DesensitizationUtil;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.ItemCommentLevelAndCountVO;
import com.icoding.vo.ItemCommentVO;
import com.icoding.vo.NewItemsCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {

  @Autowired
  ItemsMapper itemsMapper;

  @Autowired
  ItemsSpecMapper itemsSpecMapper;

  @Autowired
  ItemsParamMapper itemsParamMapper;

  @Autowired
  ItemsImgMapper itemsImgMapper;

  @Autowired
  ItemsCommentsMapper itemsCommentsMapper;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId) {
    return itemsMapper.queryItemsByCategory(rootCategoryId);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Items queryItemById(String id) {
    return itemsMapper.queryItemById(id);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<ItemsImg> queryItemImgById(String id) {
    return itemsImgMapper.queryItemImgByItemId(id);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public ItemsParam queryItemParamById(String id) {
    return itemsParamMapper.queryItemParamByItemId(id);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<ItemsSpec> queryItemSpecById(String id) {
    return itemsSpecMapper.queryItemSpecByItemId(id);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public ItemCommentLevelAndCountVO queryItemCommentLevelAndCount(String id) {
    Integer totalCounts = itemsCommentsMapper.queryItemCommentCountByItemIdAndLevel(id, CommentLevel.ALL.getType());
    Integer goodlCounts = itemsCommentsMapper.queryItemCommentCountByItemIdAndLevel(id, CommentLevel.GOOD.getType());
    Integer normalCounts = itemsCommentsMapper.queryItemCommentCountByItemIdAndLevel(id, CommentLevel.NORMAL.getType());
    Integer badCounts = itemsCommentsMapper.queryItemCommentCountByItemIdAndLevel(id, CommentLevel.BAD.getType());
    return new ItemCommentLevelAndCountVO(totalCounts, goodlCounts, normalCounts, badCounts);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public PagedGridResult<ItemCommentVO> queryItemComments(String id, Integer level, Integer page, Integer pageSize) {
    int start = (page - 1) * pageSize;
    int end = pageSize * page;
    if(level == null) {
      level = CommentLevel.ALL.getType();
    }
    int totalCounts = itemsCommentsMapper.queryItemCommentCountByItemIdAndLevel(id, level);
    int totalPages = totalCounts % pageSize;
    List<ItemCommentVO> commentList = itemsCommentsMapper.queryItemComments(id, level, start, end);
    // 脱敏
    for(ItemCommentVO comment : commentList) {
      comment.setNickname(DesensitizationUtil.commonDisplay(comment.getNickname()));
    }
    PagedGridResult<ItemCommentVO> result = new PagedGridResult<>();
    result.setPage(page);
    result.setTotal(totalPages);
    result.setRecords(totalCounts);
    result.setRows(commentList);
    return result;
  }
}
