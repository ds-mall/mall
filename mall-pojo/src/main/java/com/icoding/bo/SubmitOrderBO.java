package com.icoding.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

@ApiModel(value = "订单信息BO", description = "提交订单时的数据封装在此entity中")
public class SubmitOrderBO {
  @ApiModelProperty(value = "用户ID", name = "用户ID", example = "icoding", required = true)
  private String userId;
  @ApiModelProperty(value = "商品规格拼接字符串", name = "商品规格拼接字符串", example = "1001，1002，1003，1004", required = true)
  private String itemSpecIds;
  @ApiModelProperty(value = "地址ID", name = "地址ID", example = "1", required = true)
  private String addressId;
  @ApiModelProperty(value = "支付方式", name = "支付方式", example = "1：微信 2: 支付宝", required = true)
  private Integer payMethod;
  @ApiModelProperty(value = "留言", name = "留言", example = "使用顺丰快递", required = true)
  private String leftMsg;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getItemSpecIds() {
    return itemSpecIds;
  }

  public void setItemSpecIds(String itemSpecIds) {
    this.itemSpecIds = itemSpecIds;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public Integer getPayMethod() {
    return payMethod;
  }

  public void setPayMethod(Integer payMethod) {
    this.payMethod = payMethod;
  }

  public String getLeftMsg() {
    return leftMsg;
  }

  public void setLeftMsg(String leftMsg) {
    this.leftMsg = leftMsg;
  }
}
