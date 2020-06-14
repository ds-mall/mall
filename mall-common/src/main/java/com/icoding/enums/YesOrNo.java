package com.icoding.enums;

public enum YesOrNo {
  /**
   * 1 是
   */
  YES(1, "是"),
  /**
   * 0 否
   */
  NO(0, "否");

  private Integer type;
  private String value;
  YesOrNo(Integer type, String value) {
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
