package com.icoding.vo;

/**
 * 用户中心首页->更重状态下的订单数量汇总
 */
public class OrderStatusCountVO {
  // 待付款数量
  private Integer waitPayCounts;

  // 待发货数量
  private Integer waitDeliverCounts;

  // 待收货数量
  private Integer waitReceiveCounts;

  // 待评论数量
  private Integer waitCommentCounts;

  public Integer getWaitPayCounts() {
    return waitPayCounts;
  }

  public void setWaitPayCounts(Integer waitPayCounts) {
    this.waitPayCounts = waitPayCounts;
  }

  public Integer getWaitDeliverCounts() {
    return waitDeliverCounts;
  }

  public void setWaitDeliverCounts(Integer waitDeliverCounts) {
    this.waitDeliverCounts = waitDeliverCounts;
  }

  public Integer getWaitReceiveCounts() {
    return waitReceiveCounts;
  }

  public void setWaitReceiveCounts(Integer waitReceiveCounts) {
    this.waitReceiveCounts = waitReceiveCounts;
  }

  public Integer getWaitCommentCounts() {
    return waitCommentCounts;
  }

  public void setWaitCommentCounts(Integer waitCommentCounts) {
    this.waitCommentCounts = waitCommentCounts;
  }
}
