package com.icoding.service.impl;

import com.icoding.bo.SubmitOrderBO;
import com.icoding.enums.YesOrNo;
import com.icoding.enums.OrderStatusEnum;
import com.icoding.mapper.*;
import com.icoding.pojo.*;
import com.icoding.service.ItemsService;
import com.icoding.service.OrdersService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrdersServiceImpl implements OrdersService {

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
   * @param orderStatus
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateOrderStatus(String orderId, Integer orderStatus) {
    orderStatusMapper.updateOrderStatus(orderId, orderStatus);
  }
}
