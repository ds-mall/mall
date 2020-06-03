package com.icoding.service;

import com.icoding.pojo.Items;
import com.icoding.pojo.ItemsImg;
import com.icoding.pojo.ItemsParam;
import com.icoding.pojo.ItemsSpec;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.ItemCommentLevelAndCountVO;
import com.icoding.vo.ItemCommentVO;
import com.icoding.vo.NewItemsCategoryVO;
import com.icoding.vo.SearchItemsVO;

import java.util.List;

public interface ItemsService {
  /**
   * 查询首页分类推荐商品
   * @param rootCategoryId
   * @return
   */
  List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId);

  /**
   * 根据商品id查询商品信息
   * @param id
   * @return
   */
  Items queryItemById(String id);

  /**
   * 根据商品id查询商品图片
   * @param id
   * @return
   */
  List<ItemsImg> queryItemImgById(String id);

  /**
   * 根据商品id查询商品参数
   * @param id
   * @return
   */
  ItemsParam queryItemParamById(String id);

  /**
   * 根据商品id 查询商品规格
   * @param id
   * @return
   */
  List<ItemsSpec> queryItemSpecById(String id);

  /**
   * 根据商品id 查询评论等级及对应数量
   * @param id
   * @return
   */
  ItemCommentLevelAndCountVO queryItemCommentLevelAndCount(String id);

  /**
   * 查询商品指定等级的评论列表
   * @param id
   * @param level
   * @param page
   * @param pageSize
   * @return
   */
  PagedGridResult<ItemCommentVO> queryItemComments(String id, Integer level, Integer page, Integer pageSize);

  /**
   * 根据关键字和排序规则查询商品列表
   * @param keywords
   * @param sort
   * @param page
   * @param pageSize
   * @return
   */
  PagedGridResult<SearchItemsVO> queryItemByKeywords(String keywords, String sort, Integer page, Integer pageSize);

  /**
   * 根据三级分类ID查询商品列表
   * @param catId
   * @param sort
   * @param page
   * @param pageSize
   * @return
   */
  PagedGridResult<SearchItemsVO> queryItemByCategoryLevelThree(Integer catId, String sort, Integer page, Integer pageSize);

  /**
   * 提交订单后扣减库存
   * @param specId
   * @param buyCount
   */
  void decreaseItemSpecStock(String specId, Integer buyCount);
}
