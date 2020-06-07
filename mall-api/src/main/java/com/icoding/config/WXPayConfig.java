package com.icoding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:payment.properties")
@ConfigurationProperties(prefix = "wxpay")
public class WXPayConfig {
  private String nativeUrl;
  private String mchid;
  private String privateKey;
  private String notifyUrl;
  private String orderStatusCheckUrl;

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
