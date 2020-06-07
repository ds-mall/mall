package com.icoding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icoding.config.WXPayConfig;
import com.icoding.enums.OrderStatusEnum;
import com.icoding.enums.YesOrNo;
import com.icoding.mapper.OrderStatusMapper;
import com.icoding.pojo.OrderStatus;
import com.icoding.utils.DateUtil;
import com.icoding.utils.JSONResult;
import com.icoding.utils.SignUtil;
import com.icoding.vo.PayResultVO;
import com.icoding.vo.PayjsNativeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Api(value = "支付", tags = {"支付模块相关接口"})
@RestController
@RequestMapping("/pay")
public class PayController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WXPayConfig wxPayConfig;

  @Autowired
  OrderStatusMapper orderStatusMapper;

  @ApiOperation(value = "微信扫码支付", notes = "微信扫码支付", httpMethod = "POST")
  @PostMapping(value = "/getWXPayQRCode")
  public JSONResult nativePay(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId){
    Map<String,String> map = new HashMap<>();
    // 商户号
    map.put("mchid", wxPayConfig.getMchid());
    // 金额(单位:分)
    map.put("total_fee","" + 1);
    // 商城内部订单号
    map.put("out_trade_no", orderId);
    // 订单标题
    String body = String.format("吃货多多-付款用户[%s]", userId);
    map.put("body", body);
    // 接收微信支付异步通知的回调地址。必须为可直接访问的URL，不能带参数、session验证、csrf验证。留空则不通知
    map.put("notify_url", wxPayConfig.getNotifyUrl());
    // 数据签名（签名算法:https://help.payjs.cn/api-lie-biao/qian-ming-suan-fa.html）
    String md5 = SignUtil.sign(map, wxPayConfig.getPrivateKey());

    PayjsNativeVO payjsNativeVO = PayjsNativeVO.PayjsNativeVOBuilder.aPayjsNativeVO()
            .withMchid(wxPayConfig.getMchid())
            .withTotal_fee(1) // 为方便测试，所有金额设置为 1分钱
            .withOut_trade_no(orderId)
            .withBody(body)
            .withNotify_url(wxPayConfig.getNotifyUrl())
            .withSign(md5.toUpperCase())
            .build();

    /**
     * 调用 PAYJS Native 扫码支付（主扫） API： https://help.payjs.cn/api-lie-biao/sao-ma-zhi-fu.html
     */

    OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
    if(orderStatus.getOrderStatus().equals(OrderStatusEnum.WATI_PAY.getType())) {
      // TODO 从redis中去获得这笔订单的微信支付二维码，如果订单状态没有支付没有就放入，这样的做法防止用户频繁刷新而调用微信接口
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<PayjsNativeVO> requsetEntity = new HttpEntity(payjsNativeVO, headers);

      ResponseEntity<String> responseEntity = restTemplate.postForEntity(wxPayConfig.getNativeUrl(), requsetEntity, String.class);

      String response = responseEntity.getBody();
      // TODO 请求完成后 qrcode 放到 redis中
      return JSONResult.ok(JSONObject.parse(response));
    } else {
      return JSONResult.errMsg("该订单不存在, 或已支付");
    }
  }

  @ApiOperation(value = "查询订单状态", notes = "查询订单状态", httpMethod = "POST")
  @PostMapping(value = "/getPaidOrderInfo")
  public JSONResult getPAYJSOrderInfo(@RequestParam("payjsOrderId") String payjsOrderId) {
    // TODO PAYJS 未开放通过自身订单号查询订单详情

    Map<String,String> map = new HashMap<>();
    // PAYJS 平台订单号
    map.put("payjs_order_id", payjsOrderId);
    // 数据签名
    String md5 = SignUtil.sign(map, wxPayConfig.getPrivateKey());
    map.put("sign", md5.toUpperCase());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<HashMap> requsetEntity = new HttpEntity(map, headers);

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(wxPayConfig.getOrderStatusCheckUrl(), requsetEntity, String.class);
    String response = responseEntity.getBody();
    PayResultVO payResultVO = JSON.parseObject(response, PayResultVO.class);

    if(payResultVO.getStatus() == YesOrNo.YES.getType()) {
      LOGGER.info("*************订单: {} 支付成功 - 时间: {} *************", payResultVO.getOutTradeNo() ,payResultVO.getPaidTime());
      LOGGER.info("* 商户订单号: {}", payResultVO.getPayjsOrderId());
      LOGGER.info("* 微信订单号: {}", payResultVO.getTransactionId());
      LOGGER.info("* 实际支付金额: {}", payResultVO.getTotalFee());
      LOGGER.info("*****************************************************************************");
    }
    return JSONResult.ok(JSONObject.parse(response));
  }
}
