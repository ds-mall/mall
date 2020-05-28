package com.icoding.enums;

public enum CategoryType {
  ONE(1, "一级分类"),
  TWO(2, "二级分类"),
  THREE(3, "三级分类");
  private Integer type;
  private String value;

  CategoryType(Integer type, String value) {
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
