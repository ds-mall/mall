package com.icoding.vo;

/**
 * 查询PAYJS订单状态返回结果封装
 */
public class PayResultVO {
  // 用户端 订单号
  private String outTradeNo;
  // PAYJS 订单号
  private String payjsOrderId;
  // 微信端 订单号(非必填)
  private String transactionId;
  // 订单状态(0：未支付，1：支付成功)
  private Integer status;
  // 订单金额
  private Integer totalFee;
  // 支付时间
  private String paidTime;

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getPayjsOrderId() {
    return payjsOrderId;
  }

  public void setPayjsOrderId(String payjsOrderId) {
    this.payjsOrderId = payjsOrderId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Integer totalFee) {
    this.totalFee = totalFee;
  }

  public String getPaidTime() {
    return paidTime;
  }

  public void setPaidTime(String paidTime) {
    this.paidTime = paidTime;
  }

  @Override
  public String toString() {
    return "PayResultVO{" +
            "outTradeNo='" + outTradeNo + '\'' +
            ", payjsOrderId='" + payjsOrderId + '\'' +
            ", transactionId='" + transactionId + '\'' +
            ", status=" + status +
            ", totalFee=" + totalFee +
            ", paidTime='" + paidTime + '\'' +
            '}';
  }
}
