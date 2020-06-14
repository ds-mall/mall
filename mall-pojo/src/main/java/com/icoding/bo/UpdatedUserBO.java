package com.icoding.bo;

import com.icoding.pojo.Users;
import com.icoding.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "更新后用户信息BO", description = "更新后用户信息BO")
public class UpdatedUserBO {
  @ApiModelProperty(value = "用户昵称", name = "用户昵称", example = "icoding", required = true)
  private String nickname;
  @ApiModelProperty(value = "真实姓名", name = "真实姓名", example = "张三", required = false)
  private String realname;
  @ApiModelProperty(value = "手机号", name = "手机号", example = "12345678901", required = false)
  private String mobile;
  @ApiModelProperty(value = "邮箱", name = "邮箱", example = "317010370@qq.com", required = false)
  private String email;
  @ApiModelProperty(value = "生日", name = "生日", example = "1992-01-15", required = false)
  private String birthday;
  @ApiModelProperty(value = "性别", name = "性别", example = "1:男  0:女  2:保密", required = true)
  private Integer sex;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  @Override
  public String toString() {
    return "UpdatedUserBO{" +
            "nickname='" + nickname + '\'' +
            ", realname='" + realname + '\'' +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", birthday='" + birthday + '\'' +
            ", sex=" + sex +
            '}';
  }

  public Users convertToPojo() {
    return Users.UsersBuilder.anUsers()
            .withNickname(nickname)
            .withRealname(realname)
            .withSex(sex)
            .withBirthday(DateUtil.stringToDate(birthday))
            .withMobile(mobile)
            .withEmail(email)
            .build();
  }
}
