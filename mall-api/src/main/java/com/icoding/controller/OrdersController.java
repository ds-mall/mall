package com.icoding.controller;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.bo.ShopcartItemBO;
import com.icoding.bo.SubmitOrderBO;
import com.icoding.enums.OrderStatusEnum;
import com.icoding.enums.PayMethod;
import com.icoding.enums.YesOrNo;
import com.icoding.pojo.OrderItems;
import com.icoding.pojo.Orders;
import com.icoding.service.AddressService;
import com.icoding.service.OrdersService;
import com.icoding.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.icoding.enums.RedisKey.SHOPCART;


@Api(value = "订单", tags = {"订单模块相关接口"})
@RestController
@RequestMapping("/orders")
public class OrdersController {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

  @Autowired
  AddressService addressService;

  @Autowired
  OrdersService ordersService;

  @Autowired
  RedisOperator redisOperator;

  @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
  @PostMapping("/create")
  public JSONResult create(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestBody SubmitOrderBO submitOrderBO,
          HttpServletRequest req,
          HttpServletResponse rep
  ) {
    if(StringUtils.isBlank(submitOrderBO.getItemSpecIds())) {
      return JSONResult.errMsg("没有商品信息，订单无法提交");
    }
    Integer payMethod = submitOrderBO.getPayMethod();
    if(!PayMethod.WEIXIN.getType().equals(payMethod) && PayMethod.ALIPAY.getType().equals(payMethod)) {
      return JSONResult.errMsg("支付方式不支持");
    }
    if(StringUtils.isBlank(submitOrderBO.getAddressId())) {
      return JSONResult.errMsg("收货地址为空");
    }

    String userId = submitOrderBO.getUserId();
    String shopcartJson = redisOperator.get(SHOPCART.getKey() + ":" + userId);
    if(StringUtils.isBlank(shopcartJson)) {
      return JSONResult.errMsg("购物车数据不正确");
    }
    // redis中的购物车数据
    List<ShopcartItemBO> shopcartItems = JsonUtils.jsonToList(shopcartJson, ShopcartItemBO.class);

    // 1 创建订单
    JSONResult result = ordersService.createOrder(shopcartItems, submitOrderBO);

    // 2 创建订单以后，移除购物车中已结算(已提交)的商品
    // 整合redis后，完善购物车中已结算商品的清除，并且同步到前端的cookie
    if(result.getStatus().equals(HttpStatus.OK.value())) {
      List<ShopcartItemBO> newShopcartItems = shopcartItems.stream().filter(item -> Stream.of(submitOrderBO.getItemSpecIds().split(","))
              .anyMatch(itemSpecId -> !itemSpecId.equals(item.getSpecId()))).collect(Collectors.toList());

      redisOperator.set(SHOPCART.getKey() + ":" + userId, JsonUtils.objectToJson(newShopcartItems));
      CookieUtils.setCookie(req, rep, SHOPCART.getKey(), JsonUtils.objectToJson(newShopcartItems), true);
    }


    return result;
  }

  @ApiOperation(value = "接收微信支付异步通知的回调地址", notes = "接收微信支付异步通知的回调地址", httpMethod = "POST")
  @PostMapping("/notifyCallbackOnOrderPaid")
  public Integer notifyCallbackOnOrderPaid(PayjsNotifyBO payjsNotifyBO, HttpServletRequest req) {
    LOGGER.info("***************** 支付回调 start ***************");
    LOGGER.info("支付回调携带信息- {}", payjsNotifyBO.toString());
    String orderId = payjsNotifyBO.getOut_trade_no();
    String time = payjsNotifyBO.getTime_end();
    ordersService.updateOrderStatus(orderId, time, OrderStatusEnum.WAIT_DELIVER.getType());
    LOGGER.info("***************** 支付回调 end ***************");
    return HttpStatus.OK.value();
  }


  /**
   * 暂时没有后端oms， 所以该接口用于模拟发货
   * @param orderId
   * @return
   */
  @ApiOperation(value = "商家发货", notes = "商家发货并修改订单状态为30", httpMethod = "POST")
  @PostMapping("/deliver")
  public JSONResult deliver(@RequestParam("orderId") String orderId) {
    LOGGER.info("***************** 商家发货 start ***************");
    ordersService.updateOrderStatus(orderId, DateUtil.getCurrentDateString(), OrderStatusEnum.WAIT_RECEIVE.getType());
    LOGGER.info("***************** 商家发货 end ***************");
    return JSONResult.ok("商家发货成功");
  }

  @ApiOperation(value = "用户确认收货", notes = "用户确认收货并修改订单状态为40", httpMethod = "POST")
  @PostMapping("/receive")
  public JSONResult receive(@RequestParam("orderId") String orderId, @RequestParam("userId") String userId) {
    JSONResult result = ordersService.checkOrder(userId, orderId);
    if(result.getStatus() != HttpStatus.OK.value()) {
      return result;
    }

    LOGGER.info("***************** 确认收货 start ***************");
    ordersService.updateOrderStatus(orderId, DateUtil.getCurrentDateString(), OrderStatusEnum.SUCCESS.getType());
    LOGGER.info("***************** 确认收货 end ***************");
    return result;
  }

  @ApiOperation(value = "查询订单商品", notes = "查询订单商品", httpMethod = "GET")
  @GetMapping("/items")
  public JSONResult getItemsByOrderId(
          @RequestParam("userId") String userId,
          @RequestParam("orderId") String orderId) {
    JSONResult result = ordersService.checkOrder(userId, orderId);
    if(result.getStatus() != HttpStatus.OK.value()) {
      return result;
    }
    Orders order = (Orders)result.getData();
    if(YesOrNo.YES.getType().equals(order.getIsComment())) {
      return JSONResult.errMsg("该笔订单已完成评价");
    }

    List<OrderItems> orderItems = ordersService.getItemsByOrderId(orderId);
    return JSONResult.ok(orderItems);
  }
}
