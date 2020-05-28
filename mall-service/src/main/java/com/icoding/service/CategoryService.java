package com.icoding.service;

import com.icoding.pojo.Category;
import com.icoding.vo.SecondLevelCategoryVO;

import java.util.List;

public interface CategoryService {
  List<Category> queryCategoryByType(Integer type);
  List<SecondLevelCategoryVO> queryCategoryByFatherId(Integer fatherId);
}
