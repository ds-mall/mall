package com.icoding.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 自定义响应数据结构
 *              本类可提供给H5/IOS/安卓/公众号/小程序 使用
 *              前端接受此类数据json object后，可自行根据业务去实现相关功能
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // 去除null字段
public class JSONResult {
  // 定义jackson对象
  private static final ObjectMapper MAPPER = new ObjectMapper();

  // 响应业务状态
  private Integer status;

  // 响应消息
  private String msg;

  // 响应数据
  private Object data;

  public JSONResult(Integer status, String msg, Object data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public JSONResult(Object data) {
    this.status = 200;
    this.msg = "ok";
    this.data = data;
  }

  public JSONResult(Integer status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  public static JSONResult build(Integer status, String msg, Object data) {
    return new JSONResult(status, msg, data);
  }

  public static JSONResult ok(Object data) {
    return new JSONResult(data);
  }

  public static JSONResult ok() {
    return new JSONResult(null);
  }

  public static JSONResult errMsg(String msg) {
    return new JSONResult(500, msg);
  }

  public static JSONResult errStatusAndMsg(Integer status, String msg) {
    return new JSONResult(status, msg);
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
