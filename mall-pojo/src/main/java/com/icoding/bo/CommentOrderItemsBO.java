package com.icoding.bo;

import com.icoding.pojo.ItemsComments;

/**
 * 接收新建评论传递过来的数据
 */
public class CommentOrderItemsBO {
  private String commentId;
  private Integer commentLevel;
  private String content;
  private String itemId;
  private String itemName;
  private String itemSpecId;
  private String itemSpecName;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public Integer getCommentLevel() {
    return commentLevel;
  }

  public void setCommentLevel(Integer commentLevel) {
    this.commentLevel = commentLevel;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

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

  public String getItemSpecId() {
    return itemSpecId;
  }

  public void setItemSpecId(String itemSpecId) {
    this.itemSpecId = itemSpecId;
  }

  public String getItemSpecName() {
    return itemSpecName;
  }

  public void setItemSpecName(String itemSpecName) {
    this.itemSpecName = itemSpecName;
  }

  @Override
  public String toString() {
    return "CommentOrderItemsBO{" +
            "commentId='" + commentId + '\'' +
            ", commentLevel=" + commentLevel +
            ", content='" + content + '\'' +
            ", itemId='" + itemId + '\'' +
            ", itemName='" + itemName + '\'' +
            ", itemSpecId='" + itemSpecId + '\'' +
            ", itemSpecName='" + itemSpecName + '\'' +
            '}';
  }

  public ItemsComments convertToPojo() {
    ItemsComments itemsComments = new ItemsComments();
    itemsComments.setId(commentId);
    itemsComments.setCommentLevel(commentLevel);
    itemsComments.setContent(content);
    itemsComments.setItemId(itemId);
    itemsComments.setItemName(itemName);
    itemsComments.setItemSpecId(itemSpecId);
    itemsComments.setSpecName(itemSpecName);
    return itemsComments;
  }
}
