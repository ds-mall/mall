package com.icoding.controller;

import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class HelloController {

  @Autowired
  private UsersService usersService;

  @GetMapping("/{id}")
  public Users getUserById(@PathVariable("id") int id) {
    return usersService.getUsersById(id);
  }
}
