package com.icoding.vo;

import java.util.List;

/**
 * 首页展示各一级分类的最新六款商品
 */
public class NewItemsCategoryVO {
  private Integer rootCatId;
  private String rootCatName;
  private String slogan;
  private String catImage;
  private String bgColor;
  private List<SimpleItemVO> simpleItemList;

  public Integer getRootCatId() {
    return rootCatId;
  }

  public void setRootCatId(Integer rootCatId) {
    this.rootCatId = rootCatId;
  }

  public String getRootCatName() {
    return rootCatName;
  }

  public void setRootCatName(String rootCatName) {
    this.rootCatName = rootCatName;
  }

  public String getSlogan() {
    return slogan;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public String getCatImage() {
    return catImage;
  }

  public void setCatImage(String catImage) {
    this.catImage = catImage;
  }

  public String getBgColor() {
    return bgColor;
  }

  public void setBgColor(String bgColor) {
    this.bgColor = bgColor;
  }

  public List<SimpleItemVO> getSimpleItemList() {
    return simpleItemList;
  }

  public void setSimpleItemList(List<SimpleItemVO> simpleItemList) {
    this.simpleItemList = simpleItemList;
  }

  @Override
  public String toString() {
    return "NewItemsCategoryVO{" +
            "rootCatId=" + rootCatId +
            ", rootCatName='" + rootCatName + '\'' +
            ", slogan='" + slogan + '\'' +
            ", catImage='" + catImage + '\'' +
            ", bgColor='" + bgColor + '\'' +
            ", simpleItemList=" + simpleItemList +
            '}';
  }
}
