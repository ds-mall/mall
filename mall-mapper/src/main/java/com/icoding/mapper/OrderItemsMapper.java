package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderItems;
import org.apache.ibatis.annotations.Param;

public interface OrderItemsMapper extends MyMapper<OrderItems> {
  void deleteOrderItems(@Param("orderId") String orderId);
}
