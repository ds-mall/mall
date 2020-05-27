package com.icoding.bo;

import com.icoding.pojo.Users;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象BO", description = "用户输入的数据封装在次entity中")
public class UserBO {
  @ApiModelProperty(value = "用户名", name = "用户名", example = "icoding", required = true)
  private String username;
  @ApiModelProperty(value = "密码", name = "密码", example = "1234567", required = true)
  private String password;
  @ApiModelProperty(value = "确认密码", name = "确认密码", example = "1234567", required = false) // 登录时不需要确认密码
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
