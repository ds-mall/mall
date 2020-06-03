package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.ItemsSpec;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {
  List<ItemsSpec> queryItemSpecByItemId(String id);
  int decreaseItemSpecStockBySpecId(@Param("specId") String specId, @Param("buyCount") Integer buyCount);
}
