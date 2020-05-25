package com.icoding.enums;

/**
 * @description 用户性别
 */
public enum Sex {
  MAN(1, "男"),
  WOMAN(0, "女"),
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
