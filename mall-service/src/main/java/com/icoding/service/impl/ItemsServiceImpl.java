package com.icoding.service.impl;

import com.icoding.mapper.ItemsMapper;
import com.icoding.service.ItemsService;
import com.icoding.vo.NewItemsCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {

  @Autowired
  ItemsMapper itemsMapper;

  @Override
  public List<NewItemsCategoryVO> queryItemsByCategory(Integer rootCategoryId) {
    return itemsMapper.queryItemsByCategory(rootCategoryId);
  }
}
