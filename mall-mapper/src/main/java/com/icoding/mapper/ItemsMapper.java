package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Items;
import com.icoding.vo.NewItemsCategoryVO;
import com.icoding.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends MyMapper<Items> {
  List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId);
  Items queryItemById(String id);

  int queryItemsCountByKeywords(String keywords);
  List<SearchItemsVO> queryItemsByKeywords(@Param("queryParams") Map<String, Object> queryParams);

  int queryItemsCountByCagegoryLevelThree(Integer catId);
  List<SearchItemsVO> queryItemsByCategoryLevelThree(@Param("queryParams") Map<String, Object> queryParams);
}
