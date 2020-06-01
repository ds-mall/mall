package com.icoding.bo;

import com.icoding.pojo.UserAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

@ApiModel(value = "用户地址对象BO", description = "用户输入的地址信息数据封装在此entity中")
public class UserAddressBO {
  @ApiModelProperty(value = "地址id", name = "地址id", example = "icoding", required = false)
  private String addressId;
  @ApiModelProperty(value = "用户id", name = "用户id", example = "icoding", required = true)
  private String userId;
  @ApiModelProperty(value = "收货人", name = "收货人", example = "张三", required = true)
  private String receiver;
  @ApiModelProperty(value = "手机号", name = "手机号", example = "13333333333", required = true)
  private String mobile;
  @ApiModelProperty(value = "省份", name = "省份", example = "山东省", required = true)
  private String province;
  @ApiModelProperty(value = "城市", name = "城市", example = "青岛市", required = true)
  private String city;
  @ApiModelProperty(value = "区县", name = "区县", example = "西海岸新区", required = true)
  private String district;
  @ApiModelProperty(value = "详细地址", name = "详细地址", example = "xxx街道xxx小区", required = true)
  private String detail;

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  /**
   * UserAddressBO 转换为 UserAddress
   * @return
   */
  public UserAddress converToPoJo() {
    UserAddress userAddress = new UserAddress();
    if(StringUtils.isNotBlank(this.addressId)) {
      // 更新操作 addressId不为空
      userAddress.setId(addressId);
    }
    userAddress.setUserId(this.userId);
    userAddress.setReceiver(this.receiver);
    userAddress.setMobile(this.mobile);
    userAddress.setProvince(this.province);
    userAddress.setCity(this.city);
    userAddress.setDistrict(this.district);
    userAddress.setDetail(this.detail);
    return userAddress;
  }
}
