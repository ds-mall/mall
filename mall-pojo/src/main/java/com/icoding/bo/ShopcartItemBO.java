package com.icoding.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "购物车商品对象BO", description = "购物车商品的数据封装在次entity中")
public class ShopcartItemBO {
  @ApiModelProperty(value = "商品id", name = "商品id", example = "cake-1001", required = true)
  private String itemId;
  @ApiModelProperty(value = "商品主图url", name = "商品主图url", example = "http://122.152.205.72:88/foodie/cake-1001/img1.png", required = true)
  private String itemImgUrl;
  @ApiModelProperty(value = "商品名称", name = "商品名称", example = "【爱吃多多】真香预警 超级好吃 手撕面包 儿童早餐早饭", required = true)
  private String itemName;
  @ApiModelProperty(value = "商品规格id", name = "商品规格id", example = "1", required = true)
  private String specId;
  @ApiModelProperty(value = "商品规格名称", name = "商品规格名称", example = "原味", required = true)
  private String specName;
  @ApiModelProperty(value = "商品数量", name = "商品数量", example = "1", required = true)
  private Integer buyCounts;
  @ApiModelProperty(value = "优惠价", name = "优惠价", example = "18", required = true)
  private String priceDiscount;
  @ApiModelProperty(value = "原价", name = "原价", example = "20", required = true)
  private String priceNormal;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getItemImgUrl() {
    return itemImgUrl;
  }

  public void setItemImgUrl(String itemImgUrl) {
    this.itemImgUrl = itemImgUrl;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getSpecId() {
    return specId;
  }

  public void setSpecId(String specId) {
    this.specId = specId;
  }

  public String getSpecName() {
    return specName;
  }

  public void setSpecName(String specName) {
    this.specName = specName;
  }

  public Integer getBuyCounts() {
    return buyCounts;
  }

  public void setBuyCounts(Integer buyCounts) {
    this.buyCounts = buyCounts;
  }

  public String getPriceDiscount() {
    return priceDiscount;
  }

  public void setPriceDiscount(String priceDiscount) {
    this.priceDiscount = priceDiscount;
  }

  public String getPriceNormal() {
    return priceNormal;
  }

  public void setPriceNormal(String priceNormal) {
    this.priceNormal = priceNormal;
  }

  @Override
  public String toString() {
    return "ShopcartItemBO{" +
            "itemId='" + itemId + '\'' +
            ", itemImgUrl='" + itemImgUrl + '\'' +
            ", itemName='" + itemName + '\'' +
            ", specId='" + specId + '\'' +
            ", specName='" + specName + '\'' +
            ", buyCounts=" + buyCounts +
            ", priceDiscount='" + priceDiscount + '\'' +
            ", priceNormal='" + priceNormal + '\'' +
            '}';
  }

  public static final class ShopcartItemBOBuilder {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;

    private ShopcartItemBOBuilder() {
    }

    public static ShopcartItemBOBuilder aShopcartItemBO() {
      return new ShopcartItemBOBuilder();
    }

    public ShopcartItemBOBuilder withItemId(String itemId) {
      this.itemId = itemId;
      return this;
    }

    public ShopcartItemBOBuilder withItemImgUrl(String itemImgUrl) {
      this.itemImgUrl = itemImgUrl;
      return this;
    }

    public ShopcartItemBOBuilder withItemName(String itemName) {
      this.itemName = itemName;
      return this;
    }

    public ShopcartItemBOBuilder withSpecId(String specId) {
      this.specId = specId;
      return this;
    }

    public ShopcartItemBOBuilder withSpecName(String specName) {
      this.specName = specName;
      return this;
    }

    public ShopcartItemBOBuilder withBuyCounts(Integer buyCounts) {
      this.buyCounts = buyCounts;
      return this;
    }

    public ShopcartItemBOBuilder withPriceDiscount(String priceDiscount) {
      this.priceDiscount = priceDiscount;
      return this;
    }

    public ShopcartItemBOBuilder withPriceNormal(String priceNormal) {
      this.priceNormal = priceNormal;
      return this;
    }

    public ShopcartItemBO build() {
      ShopcartItemBO shopcartItemBO = new ShopcartItemBO();
      shopcartItemBO.setItemId(itemId);
      shopcartItemBO.setItemImgUrl(itemImgUrl);
      shopcartItemBO.setItemName(itemName);
      shopcartItemBO.setSpecId(specId);
      shopcartItemBO.setSpecName(specName);
      shopcartItemBO.setBuyCounts(buyCounts);
      shopcartItemBO.setPriceDiscount(priceDiscount);
      shopcartItemBO.setPriceNormal(priceNormal);
      return shopcartItemBO;
    }
  }
}
