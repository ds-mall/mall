package com.icoding.controller;

import com.icoding.bo.SubmitOrderBO;
import com.icoding.enums.OrderStatusEnum;
import com.icoding.enums.PayMethod;
import com.icoding.service.AddressService;
import com.icoding.service.OrdersService;
import com.icoding.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "订单", tags = {"订单模块相关接口"})
@RestController
@RequestMapping("/orders")
public class OrdersController {

  @Autowired
  AddressService addressService;

  @Autowired
  OrdersService ordersService;

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
    if(payMethod != PayMethod.WEIXIN.getType() && payMethod != PayMethod.ALIPAY.getType()) {
      return JSONResult.errMsg("支付方式不支持");
    }
    if(StringUtils.isBlank(submitOrderBO.getAddressId())) {
      return JSONResult.errMsg("收货地址为空");
    }

    // 1 创建订单
    String orderId = "";
    try {
      orderId = ordersService.createOrder(submitOrderBO);
    } catch(Exception e) {
      return JSONResult.errMsg(e.getMessage());
    }

    // 2 创建订单以后，移除购物车中已结算(已提交)的商品
    // TODO 整合redis后，完善购物车中已结算商品的清除，并且同步到前端的cookie
    // CookieUtils.setCookie(req, rep, "shopCart", "", true);

    // 3 向支付中心发送当前订单， 用于保存支付中心的订单数据
    return JSONResult.ok(orderId);
  }

  @ApiOperation(value = "接收微信支付异步通知的回调地址", notes = "接收微信支付异步通知的回调地址", httpMethod = "POST")
  @PostMapping("/norifyCallbackOnOrderPaid")
  public Integer notifyCallbackOnOrderPaid(String orderId) {
    ordersService.updateOrderStatus(orderId, OrderStatusEnum.WATI_DELIVER.getType());
    return HttpStatus.OK.value();
  }
}
