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

  /**
   * 物理删除 真实的删除数据库里的订单
   * @param userId
   * @param orderId
   */
  void deleteOrder(@Param("userId") String userId, @Param("orderId") String orderId);

  /**
   * 逻辑删除 将该订单的is_delete状态更新为1
   * @param userId
   * @param orderId
   */
  void setOrderDeleted(@Param("userId") String userId, @Param("orderId") String orderId);


  Orders queryOrderByUserIdAndOrderId(@Param("userId") String userId, @Param("orderId") String orderId);
}
