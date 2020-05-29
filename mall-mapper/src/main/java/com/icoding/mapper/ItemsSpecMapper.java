package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.ItemsSpec;

import java.util.List;

public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {
  List<ItemsSpec> queryItemSpecByItemId(String id);
}
