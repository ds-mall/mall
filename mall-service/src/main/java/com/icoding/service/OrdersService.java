package com.icoding.service;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.bo.SubmitOrderBO;

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
}
