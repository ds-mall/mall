package com.icoding.service.impl;

import com.icoding.mapper.UsersMapper;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  private UsersMapper usersMapper;

  @Override
  public Users getUsersById(int id) {
    return usersMapper.selectByPrimaryKey(id);
  }

  /**
   * 判断用户是否存在
   * @param username
   * @return
   */
  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Users queryIsUserExists(String username) {
    return usersMapper.getUserByUsername(username);
  }
}
