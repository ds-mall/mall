package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemsMapper extends MyMapper<OrderItems> {
  void deleteOrderItems(@Param("orderId") String orderId);
  List<OrderItems> getOrderItemsByOrderId(@Param("orderId") String orderId);
}
