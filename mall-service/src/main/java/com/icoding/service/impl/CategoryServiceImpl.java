package com.icoding.service.impl;

import com.icoding.mapper.CategoryMapper;
import com.icoding.pojo.Category;
import com.icoding.service.CategoryService;
import com.icoding.vo.SecondLevelCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  CategoryMapper categoryMapper;

  @Override
  public List<Category> queryCategoryByType(Integer type) {
    return categoryMapper.queryCategoryByType(type);
  }

  @Override
  public List<SecondLevelCategoryVO> queryCategoryByFatherId(Integer fatherId) {
    return categoryMapper.queryCategoryByFatherId(fatherId);
  }
}
