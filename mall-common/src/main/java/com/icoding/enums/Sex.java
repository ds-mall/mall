package com.icoding.enums;

/**
 * @description 用户性别
 */
public enum Sex {
  /**
   * 男
   */
  MAN(1, "男"),
  /**
   * 女
   */
  WOMAN(0, "女"),
  /**
   * 保密
   */
  SECRET(2, "保密");

  private Integer type;
  private String value;
  Sex(Integer type, String value) {
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
