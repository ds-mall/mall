package com.icoding.vo;

import com.icoding.pojo.OrderItems;

import java.util.Date;
import java.util.List;

/**
 *  用户中心->我的订单->订单记录
 */
public class UserCenterOrderVO {
  private String orderId;
  private String orderStatus;
  private Integer realPayAmount;
  private Integer postAmount;
  private Integer payMethod;
  private Integer isComment;
  private Date createdTime;
  private List<OrderItems> subOrderItemList;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Integer getRealPayAmount() {
    return realPayAmount;
  }

  public void setRealPayAmount(Integer realPayAmount) {
    this.realPayAmount = realPayAmount;
  }

  public Integer getPostAmount() {
    return postAmount;
  }

  public void setPostAmount(Integer postAmount) {
    this.postAmount = postAmount;
  }

  public Integer getPayMethod() {
    return payMethod;
  }

  public void setPayMethod(Integer payMethod) {
    this.payMethod = payMethod;
  }

  public Integer getIsComment() {
    return isComment;
  }

  public void setIsComment(Integer isComment) {
    this.isComment = isComment;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public List<OrderItems> getSubOrderItemList() {
    return subOrderItemList;
  }

  public void setSubOrderItemList(List<OrderItems> subOrderItemList) {
    this.subOrderItemList = subOrderItemList;
  }
}
