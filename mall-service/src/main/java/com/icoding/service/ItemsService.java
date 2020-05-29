package com.icoding.service;

import com.icoding.vo.NewItemsCategoryVO;

import java.util.List;

public interface ItemsService {
  List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId);
}
