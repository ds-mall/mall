package com.icoding.controller;

import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import com.icoding.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UsersService usersService;

  @GetMapping("/{id}")
  public Users getUserById(@PathVariable("id") int id) {
    return usersService.getUsersById(id);
  }

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
}
