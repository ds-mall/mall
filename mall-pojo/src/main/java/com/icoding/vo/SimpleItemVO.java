package com.icoding.vo;

/**
 * 首页展示各一级分类下的最新六款商品
 */
public class SimpleItemVO {
  private String itemId;
  private String itemName;
  private String itemUrl;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getItemUrl() {
    return itemUrl;
  }

  public void setItemUrl(String itemUrl) {
    this.itemUrl = itemUrl;
  }
}
