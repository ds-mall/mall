package com.icoding.enums;

/**
 * 支付方式
 */
public enum PayMethod {
  /**
   * 微信支付
   */
  WEIXIN(1, "微信"),
  /**
   * 支付宝支付
   */
  ALIPAY(2, "支付宝");
  private Integer type;
  private String value;

  PayMethod(Integer type, String value) {
    this.type = type;
    this.value = value;
  }

  public Integer getType() {
    return type;
  }

  public String getValue() {
    return value;
  }
}
