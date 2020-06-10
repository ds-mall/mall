package com.icoding.service.impl;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.bo.SubmitOrderBO;
import com.icoding.enums.YesOrNo;
import com.icoding.enums.OrderStatusEnum;
import com.icoding.mapper.*;
import com.icoding.pojo.*;
import com.icoding.service.ItemsService;
import com.icoding.service.OrdersService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.UserCenterOrderVO;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceImpl.class);

  @Autowired
  Sid sid;

  @Autowired
  UserAddressMapper addressMapper;

  @Autowired
  ItemsSpecMapper itemsSpecMapper;

  @Autowired
  ItemsMapper itemsMapper;

  @Autowired
  ItemsService itemsService;

  @Autowired
  ItemsImgMapper itemsImgMapper;

  @Autowired
  OrdersMapper ordersMapper;

  @Autowired
  OrderItemsMapper orderItemMapper;

  @Autowired
  OrderStatusMapper orderStatusMapper;

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public String createOrder(SubmitOrderBO submitOrderBO) {
    String userId = submitOrderBO.getUserId();
    String addressId = submitOrderBO.getAddressId();
    String lefMsg = submitOrderBO.getLeftMsg();
    String itemSpecIds = submitOrderBO.getItemSpecIds();
    Integer payMethod = submitOrderBO.getPayMethod();
    Integer postAmount = 0; // 邮费
    // 查询订单的地址信息
    UserAddress address = addressMapper.selectByPrimaryKey(addressId);

    // 1 订单表数据保存
    String orderId = sid.nextShort();
    Orders newOrder = Orders.OrdersBuilder.anOrders()
            .withId(orderId)
            .withUserId(userId)
            .withReceiverAddress(String.format("%s省,%s市,%s,%s", address.getProvince(), address.getCity(), address.getDistrict(), address.getDetail()))
            .withReceiverName(address.getReceiver())
            .withReceiverMobile(address.getMobile())
//            .withTotalAmount()
//            .withRealPayAmount()
            .withPostAmount(postAmount)
            .withPayMethod(payMethod)
            .withLeftMsg(lefMsg)
            .withIsComment(YesOrNo.NO.getType())
            .withIsDelete(YesOrNo.NO.getType())
            .withCreatedTime(new Date())
            .withUpdatedTime(new Date())
            .build();

    // 2 根据itemSpecIds 保存订单商品信息表
    String[] itemSpecIdArr = itemSpecIds.split(",");
    int totalAmount = 0; // 商品原价累计
    int realPayAmount = 0; // 实付金额累计
    for(String itemSpecId : itemSpecIdArr) {
      // TODO 整合redis后，商品购买的数量重新从redis购物车中获取
      int buyCounts = 1;
      // 2.1 根据规格id查询 查询规格的具体信息，主要获取价格
      ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(itemSpecId);
      totalAmount += itemsSpec.getPriceNormal() * buyCounts;
      realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
      // 2.2 根据商品id获取商品信息及商品图片
      String itemId = itemsSpec.getItemId();
      Items items = itemsMapper.queryItemById(itemId);
      String imgUrl = itemsImgMapper.queryItemMainImg(itemId);
      // 2.3 循环保存子订单数据到数据库
      String orderItemId = sid.nextShort();
      OrderItems newOrderItem = OrderItems.OrderItemsBuilder.anOrderItems()
              .withId(orderItemId)
              .withItemId(itemId)
              .withItemImg(imgUrl)
              .withItemName(items.getItemName())
              .withItemSpecId(itemSpecId)
              .withItemSpecName(itemsSpec.getName())
              .withOrderId(orderId)
              .withBuyCounts(buyCounts)
              .withPrice(itemsSpec.getPriceDiscount())
              .build();
      // 2.4 订单下属商品 入库
      orderItemMapper.insert(newOrderItem);

      // 2.5 在用户提交订单以后， 需要扣减库存
      itemsService.decreaseItemSpecStock(itemSpecId, buyCounts);
    }
    newOrder.setTotalAmount(totalAmount);
    newOrder.setRealPayAmount(realPayAmount);
    // 订单数据 入库
    ordersMapper.insert(newOrder);
    // 3 保存订单状态表
    OrderStatus orderStatus = new OrderStatus();
    orderStatus.setOrderStatus(OrderStatusEnum.WATI_PAY.getType());
    orderStatus.setOrderId(orderId);
    orderStatus.setCreatedTime(new Date());
    orderStatusMapper.insert(orderStatus);

    return orderId;
  }

  /**
   * 修改订单状态
   * @param orderId
   * @param time
   * @param orderStatus
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateOrderStatus(String orderId, String time, Integer orderStatus) {
    orderStatusMapper.updateOrderStatus(orderId, time, orderStatus);
  }

  /**
   * 取消超时未支付订单(1天)
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void closeOrdersWhoseStatusIsWaitPayAndTimeOut() {
    LOGGER.info("*************** 关闭超时未支付订单 start ****************");
    List<OrderStatus> watiPayAndTimeOutOrders = orderStatusMapper.queryOrdersWhoseStatusIsWaitPayAndTimeOut();
    watiPayAndTimeOutOrders.stream().forEach(this::doClose);
    LOGGER.info("*************** 关闭超时未支付订单 end ****************");
  }

  /**
   * 用户中心->我的订单->分类订单
   * @param userId
   * @param orderStatus
   * @param page
   * @param pageSize
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public PagedGridResult<UserCenterOrderVO> queryOrdersByStatus(String userId, Integer orderStatus, Integer page, Integer pageSize) {
    if(page == null) page = 1;
    if(pageSize == null) pageSize = 20;

    int start = (page - 1) * pageSize;
    int end = pageSize * page;

    int totalCounts = ordersMapper.getOrdersCountByStatus(userId, orderStatus);
    int totalPages = totalCounts % pageSize;

    Map<String, Object> queryParams = new HashMap();
    queryParams.put("userId", userId);
    queryParams.put("orderStatus", orderStatus);
    queryParams.put("start", start);
    queryParams.put("end", end);

    List<UserCenterOrderVO> rows = ordersMapper.getOrdersByStatus(queryParams);

    PagedGridResult<UserCenterOrderVO> result = new PagedGridResult<>();
    result.setPage(page);
    result.setTotal(totalPages);
    result.setRecords(totalCounts);
    result.setRows(rows);

    return result;
  }

  /**
   * 根据用户id和订单id 将订单is_delete 状态改为1
   * @param userId
   * @param orderId
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void deleteOrder(String userId, String orderId) {
//    ordersMapper.deleteOrder(userId, orderId);
//    orderItemMapper.deleteOrderItems(orderId);
//    orderStatusMapper.deleteOrderStatus(orderId);
    ordersMapper.setOrderDeleted(userId, orderId);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Orders queryOrderByUserIdAndOrderId(String userId, String orderId) {
    return ordersMapper.queryOrderByUserIdAndOrderId(userId, orderId);
  }

  /**
   * 执行关闭订单操作
   * @param orderStatus
   */
  public void doClose(OrderStatus orderStatus) {
    orderStatusMapper.updateOrdersStatusByOrderId(orderStatus.getOrderId(), OrderStatusEnum.CLOSE.getType());
    LOGGER.info("close order: {}", orderStatus.getOrderId());
  }

  @Override
  public List<OrderItems> getItemsByOrderId(String orderId) {
    return orderItemMapper.getOrderItemsByOrderId(orderId);
  }

  @Override
  public void setOrderIsCommented(String userId, String orderId) {
    ordersMapper.setOrderIsCommented(userId, orderId);
  }

  // 用于验证用户和订单是否有关联关系，防止恶意篡改他人订单
  public JSONResult checkOrder(String userId, String orderId) {
    Orders order = queryOrderByUserIdAndOrderId(userId, orderId);
    if(order == null) {
      return JSONResult.errMsg("查无此订单");
    }
    return JSONResult.ok(order);
  }
}
