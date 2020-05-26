package com.icoding.bo;

import com.icoding.pojo.Users;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户对象BO", description = "用户输入的数据封装在次entity中")
public class UserBO {
  // 用户名
  private String username;
  // 密码
  private String password;
  // 确认密码
  private String confirmPassword;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public Users converToPojo() {
    return Users.UsersBuilder.anUsers()
            .withUsername(this.username)
            .withPassword(this.password)
            .build();
  }
}
