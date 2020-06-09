package com.icoding.mapper;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderStatus;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderStatusMapper extends MyMapper<OrderStatus> {
  void updateOrderStatus(@Param("payjsNotifyBO") PayjsNotifyBO payjsNotifyBO, @Param("orderStatus") Integer orderStatus);

  /**
   * 查询超时未支付订单
   * @return
   */
  List<OrderStatus> queryOrdersWhoseStatusIsWaitPayAndTimeOut();

  /**
   * 根据orderId 更新订单状态
   * @param orderId
   * @param orderStatus
   */
  void updateOrdersStatusByOrderId(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus);

  /**
   * 删除订单状态
   * @param orderId
   */
  void deleteOrderStatus(@Param("orderId") String orderId);
}
