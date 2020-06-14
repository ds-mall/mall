package com.icoding.vo;

/**
 * 用于展示商品搜索列表的封装
 */
public class SearchItemsVO {
  /**
   * 商品id
   */
  private String itemId;
  /**
   * 商品名称
   */
  private String itemName;
  /**
   * 累计销售
   */
  private int sellCounts;
  /**
   * 商品图片
   */
  private String imgUrl;
  /**
   * 商品价格
   */
  private int price;

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

  public int getSellCounts() {
    return sellCounts;
  }

  public void setSellCounts(int sellCounts) {
    this.sellCounts = sellCounts;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "SearchItemsVO{" +
            "itemId='" + itemId + '\'' +
            ", itemName='" + itemName + '\'' +
            ", sellCounts=" + sellCounts +
            ", imgUrl='" + imgUrl + '\'' +
            ", price=" + price +
            '}';
  }
}
