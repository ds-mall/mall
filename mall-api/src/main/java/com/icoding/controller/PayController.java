package com.icoding.controller;

import com.alibaba.fastjson.JSONObject;
import com.icoding.utils.JSONResult;
import com.icoding.utils.SignUtil;
import com.icoding.vo.PayjsNativeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
  // PAYJS Native 扫码支付（主扫） API地址
  private static final String PAYJS_NATIVE_URL = "https://payjs.cn/api/native";
  // payjs 商户号
  private static final String MCHID = "1598662141";
  // payjs 私钥
  private static final String PRIVATE_KEY = "ORXtLA9gIl3YBARL";
  // 支付成功回调接口地址
  private static final String NOTIFY_URL = "http://localhost:8088/orders/norifyCallbackOnOrderPaid";

  @Autowired
  private RestTemplate restTemplate;

  @ApiOperation(value = "微信扫码支付", notes = "微信扫码支付", httpMethod = "POST")
  @PostMapping(value = "/getWXPayQRCode")
  public JSONResult nativePay(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId){
    Map<String,String> map = new HashMap<>();
    // 商户号
    map.put("mchid", MCHID);
    // 金额(单位:分)
    map.put("total_fee","" + 123);
    // 商城内部订单号
    map.put("out_trade_no", orderId);
    // 接收微信支付异步通知的回调地址。必须为可直接访问的URL，不能带参数、session验证、csrf验证。留空则不通知
    map.put("notify_url", NOTIFY_URL);
    // 数据签名（签名算法:https://help.payjs.cn/api-lie-biao/qian-ming-suan-fa.html）
    String md5 = SignUtil.sign(map, PRIVATE_KEY);

    PayjsNativeVO payjsNativeVO = PayjsNativeVO.PayjsNativeVOBuilder.aPayjsNativeVO()
            .withMchid(MCHID)
            .withTotal_fee(123)
            .withOut_trade_no(orderId)
            .withNotify_url(NOTIFY_URL)
            .withSign(md5.toUpperCase())
            .build();

    /**
     * 调用 PAYJS Native 扫码支付（主扫） API： https://help.payjs.cn/api-lie-biao/sao-ma-zhi-fu.html
     */
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<PayjsNativeVO> requsetEntity = new HttpEntity(payjsNativeVO, headers);

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYJS_NATIVE_URL, requsetEntity, String.class);

    String response = responseEntity.getBody();

//    if(response.getStatus() != 200) {
//      return JSONResult.errMsg("创建订单失败，请联系管理员");
//    }
    return JSONResult.ok(JSONObject.parse(response));
  }
}
