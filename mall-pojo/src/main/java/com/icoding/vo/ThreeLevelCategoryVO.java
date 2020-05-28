package com.icoding.vo;

/**
 * 三级分类VO
 */
public class ThreeLevelCategoryVO {
  /**
   * 三级分类id
   */
  private Integer subId;
  /**
   * 三级分类名
   */
  private String subName;
  /**
   * 三级分类类型
   */
  private Integer subType;
  /**
   * 三级分类所属二级分类id
   */
  private Integer subFatherId;

  public Integer getSubId() {
    return subId;
  }

  public void setSubId(Integer subId) {
    this.subId = subId;
  }

  public String getSubName() {
    return subName;
  }

  public void setSubName(String subName) {
    this.subName = subName;
  }

  public Integer getSubType() {
    return subType;
  }

  public void setSubType(Integer subType) {
    this.subType = subType;
  }

  public Integer getSubFatherId() {
    return subFatherId;
  }

  public void setSubFatherId(Integer subFatherId) {
    this.subFatherId = subFatherId;
  }
}
