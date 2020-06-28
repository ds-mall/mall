package com.icoding.enums;

/**
 * @author shengding
 */

public enum RedisKey {
  /**
   * 首页轮播图
   */
  CAROUSELS("carousels"),
  /**
   * 一级分类
   */
  CATEGORIES("categories"),
  /**
   * 子分类(hash)
   */
  SUBCATEGORIES("subCategories"),
  /**
   * 用户购物车 shopcart:userId
   */
  SHOPCART("shopcart"),
  /**
   * 用户token userToken:userId
   */
  USERTOKEN("userToken"),
  ;

  private String key;


  RedisKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
