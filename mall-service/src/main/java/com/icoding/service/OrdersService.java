package com.icoding.service;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.bo.SubmitOrderBO;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.UserCenterOrderVO;

import java.util.List;

public interface OrdersService {
  /**
   * 提交订单
   * @param submitOrderBO
   * @return
   */
  String createOrder(SubmitOrderBO submitOrderBO);

  /**
   * 支付回调，更新订单状态
   * @param payjsNotifyBO
   * @param orderStatus
   */
  void updateOrderStatus(PayjsNotifyBO payjsNotifyBO, Integer orderStatus);

  /**
   * 关闭超时为支付订单 (超时限制: 下单后超过1天未支付)
   */
  void closeOrdersWhoseStatusIsWaitPayAndTimeOut();

  /**
   * 用户中心查询根据订单状态查询订单列表
   * @param userId
   * @param orderStatus
   * @param page
   * @param pageSize
   * @return
   */
  PagedGridResult<UserCenterOrderVO> queryOrdersByStatus(String userId, Integer orderStatus, Integer page, Integer pageSize);

  /**
   * 删除订单
   * @param userId
   * @param orderId
   */
  void deleteOrder(String userId, String orderId);
}
