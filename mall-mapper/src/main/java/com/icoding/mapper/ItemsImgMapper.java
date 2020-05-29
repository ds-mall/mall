package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.ItemsImg;

import java.util.List;

public interface ItemsImgMapper extends MyMapper<ItemsImg> {
  List<ItemsImg> queryItemImgByItemId(String id);
}
