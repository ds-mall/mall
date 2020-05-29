package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Items;
import com.icoding.vo.NewItemsCategoryVO;

import java.util.List;

public interface ItemsMapper extends MyMapper<Items> {
  List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId);
}
