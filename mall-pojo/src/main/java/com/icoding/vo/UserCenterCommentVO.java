package com.icoding.vo;

public class UserCenterCommentVO {
  private String commentId;
  private String content;
  private String createdTime;
  private String itemId;
  private String itemName;
  private String specName;
  private String url;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getSpecName() {
    return specName;
  }

  public void setSpecName(String specName) {
    this.specName = specName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  @Override
  public String toString() {
    return "UserCenterCommentVO{" +
            "commentId='" + commentId + '\'' +
            ", content='" + content + '\'' +
            ", createdTime='" + createdTime + '\'' +
            ", itemId='" + itemId + '\'' +
            ", itemName='" + itemName + '\'' +
            ", specName='" + specName + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
