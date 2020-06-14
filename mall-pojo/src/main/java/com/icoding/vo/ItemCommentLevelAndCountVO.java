package com.icoding.vo;

/**
 * 评论等级及对应数量
 */
public class ItemCommentLevelAndCountVO {
  /**
   * 评论总数
   */
  private Integer totalCounts;
  /**
   * 好评数
   */
  private Integer goodCounts;
  /**
   * 中评数
   */
  private Integer normalCounts;
  /**
   * 差评数
   */
  private Integer badCounts;

  public ItemCommentLevelAndCountVO(Integer totalCounts, Integer goodCounts, Integer normalCounts, Integer badCounts) {
    this.totalCounts = totalCounts;
    this.goodCounts = goodCounts;
    this.normalCounts = normalCounts;
    this.badCounts = badCounts;
  }

  public Integer getTotalCounts() {
    return totalCounts;
  }

  public void setTotalCounts(Integer totalCounts) {
    this.totalCounts = totalCounts;
  }

  public Integer getGoodCounts() {
    return goodCounts;
  }

  public void setGoodCounts(Integer goodCounts) {
    this.goodCounts = goodCounts;
  }

  public Integer getNormalCounts() {
    return normalCounts;
  }

  public void setNormalCounts(Integer normalCounts) {
    this.normalCounts = normalCounts;
  }

  public Integer getBadCounts() {
    return badCounts;
  }

  public void setBadCounts(Integer badCounts) {
    this.badCounts = badCounts;
  }

  @Override
  public String toString() {
    return "ItemCommentLevelAndCountVO{" +
            "totalCounts=" + totalCounts +
            ", goodCounts=" + goodCounts +
            ", normalCounts=" + normalCounts +
            ", badCounts=" + badCounts +
            '}';
  }
}
