package com.icoding.service;

import com.icoding.bo.ShopcartItemBO;
import com.icoding.bo.SubmitOrderBO;
import com.icoding.pojo.OrderItems;
import com.icoding.pojo.Orders;
import com.icoding.utils.JSONResult;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.OrderStatusCountVO;
import com.icoding.vo.UserCenterOrderVO;

import java.util.List;

public interface OrdersService {
  /**
   * 提交订单
   * @param shopcartItems redis中的购物车数量
   * @param submitOrderBO
   * @return
   */
  JSONResult createOrder(List<ShopcartItemBO> shopcartItems, SubmitOrderBO submitOrderBO);

  /**
   * 更新订单状态
   * @param orderId
   * @param time
   * @param orderStatus
   */
  void updateOrderStatus(String orderId, String time, Integer orderStatus);

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

  /**
   * 根据用户id和订单id查询订单
   * @param userId
   * @param orderId
   * @return
   */
  Orders queryOrderByUserIdAndOrderId(String userId, String orderId);

  /**
   * 用于验证用户和订单是否有关联关系，防止恶意篡改他人订单
   * @param userId
   * @param orderId
   * @return
   */
  JSONResult checkOrder(String userId, String orderId);


  /**
   * 根据订单id查询订单包含商品
   * @param orderId
   * @return
   */
  List<OrderItems> getItemsByOrderId(String orderId);

  /**
   * 评论成功后修改 订单的is_comment状态为1
   * @param userId
   * @param orderId
   */
  void setOrderIsCommented(String userId, String orderId);

  /**
   * 根据条件查询订单数量
   * @param userId
   * @return
   */
  OrderStatusCountVO getOrderStatusCounts(String userId);

  /**
   * 获取用户中心首页的 订单动向列表
   * @param userId
   * @param page
   * @param pageSize
   * @return
   */
  public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
