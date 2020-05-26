package com.icoding.controller;

import com.icoding.bo.UserBO;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import com.icoding.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UsersService usersService;

  @ApiIgnore
  @GetMapping("/{id}")
  public Users getUserById(@PathVariable("id") int id) {
    return usersService.getUsersById(id);
  }

  @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
  @GetMapping("usernameIsExist")
  public JSONResult usernameIsExist(@RequestParam String username) {
    // 1 用户名是否为空
    if(StringUtils.isBlank(username)) {
      return JSONResult.errMsg("用户名为空");
    }

    Users current = usersService.queryIsUserExists(username);
    // 2 用户名是否存在
    if(current != null) {
      return JSONResult.errMsg("用户名已存在");
    }

    // 3 请求成功
    return JSONResult.ok(current);
  }

  @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
  @PostMapping("/regist")
  public JSONResult regist(@RequestBody UserBO userBO) {
    String username = userBO.getUsername();
    String password = userBO.getPassword();
    String confirmPassword = userBO.getConfirmPassword();

    // 0 判断用户名和密码不能为空
    if(StringUtils.isBlank(username) ||
        StringUtils.isBlank(password) ||
        StringUtils.isBlank(confirmPassword)) {
      return JSONResult.errMsg("用户名或密码为空");
    }
    // 1 判断用户名是否存在
    if(usersService.queryIsUserExists(username) != null) {
      return JSONResult.errMsg("用户名已存在");
    }
    // 2 判断密码长度是否小于6为
    if(password.length() < 6) {
      return JSONResult.errMsg("用户密码长度不能小于6位");
    }
    // 3 判断用密码和确认密码是否相同
    if(!password.equals(confirmPassword)) {
      return JSONResult.errMsg("两次输入密码不相同");
    }
    // 4 执行注册
    Users user = usersService.createUser(userBO);
    return JSONResult.ok(user);
  }
}
