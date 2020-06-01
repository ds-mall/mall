package com.icoding.vo;

/**
 * 用户地址VO
 */
public class UserAddressVO {
  /**
   * 地址主键id
   */
  private String id;

  /**
   * 收件人姓名
   */
  private String receiver;

  /**
   * 收件人手机号
   */
  private String mobile;

  /**
   * 省份
   */
  private String province;

  /**
   * 城市
   */
  private String city;

  /**
   * 区县
   */
  private String district;

  /**
   * 详细地址
   */
  private String detail;

  /**
   * 扩展字段
   */
  private String extand;

  /**
   * 是否默认地址 1:是  0:否
   */
  private Integer isDefault;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getExtand() {
    return extand;
  }

  public void setExtand(String extand) {
    this.extand = extand;
  }

  public Integer getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Integer isDefault) {
    this.isDefault = isDefault;
  }
}
