package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Orders;
import com.icoding.vo.UserCenterOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapper extends MyMapper<Orders> {
  List<UserCenterOrderVO> getOrdersByStatus(
          @Param("queryParams") Map<String, Object> queryParams);
  int getOrdersCountByStatus(
          @Param("userId") String userId,
          @Param("orderStatus") Integer orderStatus);

  void deleteOrder(@Param("userId") String userId, @Param("orderId") String orderId);
}
