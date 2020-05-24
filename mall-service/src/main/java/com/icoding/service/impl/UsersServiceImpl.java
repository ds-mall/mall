package com.icoding.service.impl;

import com.icoding.mapper.UsersMapper;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  private UsersMapper usersMapper;

  @Override
  public Users getUsersById(int id) {
    return usersMapper.selectByPrimaryKey(id);
  }
}
