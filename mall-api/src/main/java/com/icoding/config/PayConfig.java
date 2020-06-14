package com.icoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author shengding
 */
@Configuration
public class PayConfig {

  /**
   * PAYJS Native 扫码支付（主扫） API地址
   */
  private static final String WX_NATIVE_URL = "https://payjs.cn/api/native";
  /**
   * PAYJS 商户号
   */
  private static final String WX_MACHID = "1598662141";
  /**
   * PAYJS 私钥
   */
  private static final String WX_PRIVATE_KEY = "ORXtLA9gIl3YBARL";
  /**
   * 支付成功回调接口地址
   */
  private static final String WX_NOTIFY_URL_DEV = "http://ueeff6.natappfree.cc/orders/notifyCallbackOnOrderPaid";
  private static final String WX_NOTIFY_URL_PROD = "http://172.31.87.24:8088/orders/notifyCallbackOnOrderPaid";
  /**
   * PAYJS 订单状态查询url
   */
  private static final String WX_ORDERSTATUS_CHECK_URL= "https://payjs.cn/api/check";

  @Bean("wxPayConfig")
  @Profile("dev")
  public WXPayConfig wxPayConfigDev() {
    return new WXPayConfig(
            WX_NATIVE_URL,
            WX_MACHID,
            WX_PRIVATE_KEY,
            WX_NOTIFY_URL_DEV,
            WX_ORDERSTATUS_CHECK_URL
    );
  }

    @Bean("wxPayConfig")
    @Profile("prod")
    public WXPayConfig wxPayConfigProd() {
      return new WXPayConfig(
              WX_NATIVE_URL,
              WX_MACHID,
              WX_PRIVATE_KEY,
              WX_NOTIFY_URL_PROD,
              WX_ORDERSTATUS_CHECK_URL
      );
    }


  public class WXPayConfig {
    private String nativeUrl;
    private String mchid;
    private String privateKey;
    private String notifyUrl;
    private String orderStatusCheckUrl;


    public WXPayConfig(String nativeUrl, String mchid, String privateKey, String notifyUrl, String orderStatusCheckUrl) {
      this.nativeUrl = nativeUrl;
      this.mchid = mchid;
      this.privateKey = privateKey;
      this.notifyUrl = notifyUrl;
      this.orderStatusCheckUrl = orderStatusCheckUrl;
    }

    public String getNativeUrl() {
      return nativeUrl;
    }

    public void setNativeUrl(String nativeUrl) {
      this.nativeUrl = nativeUrl;
    }

    public String getMchid() {
      return mchid;
    }

    public void setMchid(String mchid) {
      this.mchid = mchid;
    }

    public String getPrivateKey() {
      return privateKey;
    }

    public void setPrivateKey(String privateKey) {
      this.privateKey = privateKey;
    }

    public String getNotifyUrl() {
      return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
      this.notifyUrl = notifyUrl;
    }

    public String getOrderStatusCheckUrl() {
      return orderStatusCheckUrl;
    }

    public void setOrderStatusCheckUrl(String orderStatusCheckUrl) {
      this.orderStatusCheckUrl = orderStatusCheckUrl;
    }
  }
}
