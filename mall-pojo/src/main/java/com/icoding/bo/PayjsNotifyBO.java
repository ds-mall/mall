package com.icoding.bo;

/**
 * PAYJS 支付回调BO
 */
public class PayjsNotifyBO {

  /**
   * 金额。单位：分
   */
  private Integer total_fee;

  /**
   * 用户侧订单号
   */
  private String out_trade_no;

  /**
   * payjs端订单号
   */
  private String payjs_order_id;

  /**
   * 微信侧支付订单号
   */
  private String transaction_id;

  /**
   * 订单结束时间
   */
  private String time_end;

  /**
   * 用户openid
   */
  private String openid;

  /**
   * 用户自定义数据
   */
  private String attach;

  /**
   * 商户号
   */
  private String mchid;

  /**
   * 签名
   */
  private String sign;

  @Override
  public String toString() {
    return "PayjsNotifyBO{" +
            "total_fee=" + total_fee +
            ", out_trade_no='" + out_trade_no + '\'' +
            ", payjs_order_id='" + payjs_order_id + '\'' +
            ", transaction_id='" + transaction_id + '\'' +
            ", time_end='" + time_end + '\'' +
            ", openid='" + openid + '\'' +
            ", attach='" + attach + '\'' +
            ", mchid='" + mchid + '\'' +
            ", sign='" + sign + '\'' +
            '}';
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

  public String getPayjs_order_id() {
    return payjs_order_id;
  }

  public void setPayjs_order_id(String payjs_order_id) {
    this.payjs_order_id = payjs_order_id;
  }

  public String getTransaction_id() {
    return transaction_id;
  }

  public void setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
  }

  public String getTime_end() {
    return time_end;
  }

  public void setTime_end(String time_end) {
    this.time_end = time_end;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getAttach() {
    return attach;
  }

  public void setAttach(String attach) {
    this.attach = attach;
  }

  public String getMchid() {
    return mchid;
  }

  public void setMchid(String mchid) {
    this.mchid = mchid;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
