package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderStatus;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusMapper extends MyMapper<OrderStatus> {
  void updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus);
}
