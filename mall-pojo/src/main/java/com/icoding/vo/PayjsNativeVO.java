package com.icoding.vo;

/**
 * 调用PAYJS native 扫码支付接口时 传入的参数
 * 请求地址：https://payjs.cn/api/native
 */
public class PayjsNativeVO {
  // 商户号
  private String mchid;
  // 金额。单位：分
  private Integer total_fee;
  // 用户端自主生成的订单号
  private String out_trade_no;
  // 订单标题
  private String body;
  // 接收微信支付异步通知的回调地址。必须为可直接访问的URL，不能带参数、session验证、csrf验证。留空则不通知
  private String notify_url;
  // 用户自定义数据，在notify的时候会原样返回
  private String attach;
  // 支付宝交易传值：alipay ，微信支付无需此字段
  private String type;
  // 数据签名 详见 https://help.payjs.cn/api-lie-biao/qian-ming-suan-fa.html
  private String sign;

  public String getMchid() {
    return mchid;
  }

  public void setMchid(String mchid) {
    this.mchid = mchid;
  }

  public Integer getTotal_fee() {
    return total_fee;
  }

  public void setTotal_fee(Integer total_fee) {
    this.total_fee = total_fee;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getNotify_url() {
    return notify_url;
  }

  public void setNotify_url(String notify_url) {
    this.notify_url = notify_url;
  }

  public String getAttach() {
    return attach;
  }

  public void setAttach(String attach) {
    this.attach = attach;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }


  public static final class PayjsNativeVOBuilder {
    // 商户号
    private String mchid;
    // 金额。单位：分
    private Integer total_fee;
    // 用户端自主生成的订单号
    private String out_trade_no;
    // 订单标题
    private String body;
    // 接收微信支付异步通知的回调地址。必须为可直接访问的URL，不能带参数、session验证、csrf验证。留空则不通知
    private String notify_url;
    // 用户自定义数据，在notify的时候会原样返回
    private String attach;
    // 支付宝交易传值：alipay ，微信支付无需此字段
    private String type;
    // 数据签名 详见 https://help.payjs.cn/api-lie-biao/qian-ming-suan-fa.html
    private String sign;

    private PayjsNativeVOBuilder() {
    }

    public static PayjsNativeVOBuilder aPayjsNativeVO() {
      return new PayjsNativeVOBuilder();
    }

    public PayjsNativeVOBuilder withMchid(String mchid) {
      this.mchid = mchid;
      return this;
    }

    public PayjsNativeVOBuilder withTotal_fee(Integer total_fee) {
      this.total_fee = total_fee;
      return this;
    }

    public PayjsNativeVOBuilder withOut_trade_no(String out_trade_no) {
      this.out_trade_no = out_trade_no;
      return this;
    }

    public PayjsNativeVOBuilder withBody(String body) {
      this.body = body;
      return this;
    }

    public PayjsNativeVOBuilder withNotify_url(String notify_url) {
      this.notify_url = notify_url;
      return this;
    }

    public PayjsNativeVOBuilder withAttach(String attach) {
      this.attach = attach;
      return this;
    }

    public PayjsNativeVOBuilder withType(String type) {
      this.type = type;
      return this;
    }

    public PayjsNativeVOBuilder withSign(String sign) {
      this.sign = sign;
      return this;
    }

    public PayjsNativeVO build() {
      PayjsNativeVO payjsNativeVO = new PayjsNativeVO();
      payjsNativeVO.setMchid(mchid);
      payjsNativeVO.setTotal_fee(total_fee);
      payjsNativeVO.setOut_trade_no(out_trade_no);
      payjsNativeVO.setBody(body);
      payjsNativeVO.setNotify_url(notify_url);
      payjsNativeVO.setAttach(attach);
      payjsNativeVO.setType(type);
      payjsNativeVO.setSign(sign);
      return payjsNativeVO;
    }
  }
}
