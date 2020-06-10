package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderStatus;
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

  /**
   * 评论成功后 修改订单的 is_comment状态为1
   * @param userId
   * @param orderId
   */
  void setOrderIsCommented(@Param("userId") String userId, @Param("orderId") String orderId);

  /**
   * 查询各订单状态下的订单数量
   * @param queryParams
   * @return
   */
  int getMyOrderStatusCounts(@Param("queryParams") Map<String, Object> queryParams);

  int getOrderTrendCounts(@Param("userId") String userId);

  List<OrderStatus> getOrderTrendList(@Param("queryParams") Map<String, Object> map);
}
