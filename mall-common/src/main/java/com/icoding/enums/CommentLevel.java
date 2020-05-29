package com.icoding.enums;

public enum CommentLevel {
  GOOD(1, "好评"),
  NORMAL(2, "中评"),
  BAD(3, "差评"),
  ALL(4, "所有");

  private Integer type;
  private String value;

  CommentLevel(Integer type, String value) {
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
