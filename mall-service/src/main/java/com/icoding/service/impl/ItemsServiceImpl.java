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
import com.icoding.vo.SearchItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public PagedGridResult<SearchItemsVO> queryItemByKeywords(String keywords, String sort, Integer page, Integer pageSize) {
    if(page == null) {
      page = 1;
    }
    if(pageSize == null) {
      pageSize = 20;
    }

    int start = (page - 1) * pageSize;
    int end = pageSize * page;

    int totalCounts = itemsMapper.queryItemsCountByKeywords(keywords);
    int totalPages = totalCounts % pageSize;

    Map<String, Object> queryParams = new HashMap(4);
    queryParams.put("keywords", keywords);
    queryParams.put("sort", sort);
    queryParams.put("start", start);
    queryParams.put("end", end);
    List<SearchItemsVO> searchItemsList = itemsMapper.queryItemsByKeywords(queryParams);

    PagedGridResult<SearchItemsVO> result = new PagedGridResult<>();
    result.setPage(page);
    result.setTotal(totalPages);
    result.setRecords(totalCounts);
    result.setRows(searchItemsList);

    return result;
  }

  @Override
  public PagedGridResult<SearchItemsVO> queryItemByCategoryLevelThree(Integer catId, String sort, Integer page, Integer pageSize) {
    if(page == null) {
      page = 1;
    }
    if(pageSize == null) {
      pageSize = 20;
    }

    int start = (page - 1) * pageSize;
    int end = pageSize * page;

    int totalCounts = itemsMapper.queryItemsCountByCagegoryLevelThree(catId);
    int totalPages = totalCounts % pageSize;

    Map<String, Object> queryParams = new HashMap(4);
    queryParams.put("catId", catId);
    queryParams.put("sort", sort);
    queryParams.put("start", start);
    queryParams.put("end", end);
    List<SearchItemsVO> searchItemsList = itemsMapper.queryItemsByCategoryLevelThree(queryParams);

    PagedGridResult<SearchItemsVO> result = new PagedGridResult<>();
    result.setPage(page);
    result.setTotal(totalPages);
    result.setRecords(totalCounts);
    result.setRows(searchItemsList);

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void decreaseItemSpecStock(String specId, Integer buyCount) {
    /**
     * 并发条件下是会存在超卖现象的 如何解决
     * 1 synchronized：不推荐使用，集群下无用，性能地下
     * 2 锁数据库: 不推荐使用, 导致数据库性能地下
     * 3 分布式锁: zookeeper redis (终极方案)
     */

    /**
     * TODO 后续会使用zookeeper和redis 解决集群和分布式环境下的数据一致性
     * LockUtils.lock() // 加锁
     * // 1 查询当前库存
     * int currentStock = 10;
     *
     * // 2 判断是否超卖
     * if(currentStock - buyCount < 0) {
     * // 3 扣减库存
     * }
     * LockUtils.unlock() // 解 锁    *
     */

    int result = itemsSpecMapper.decreaseItemSpecStockBySpecId(specId, buyCount);
    if(result != 1) {
      throw new RuntimeException("订单创建失败, 原因：库存不足！");
    }
  }
}
