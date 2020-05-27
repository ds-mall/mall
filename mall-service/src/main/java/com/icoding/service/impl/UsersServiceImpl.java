package com.icoding.service.impl;

import com.icoding.bo.UserBO;
import com.icoding.enums.Sex;
import com.icoding.mapper.UsersMapper;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import com.icoding.utils.DateUtil;
import com.icoding.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired
  private UsersMapper usersMapper;

  @Autowired
  private Sid sid;

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

  /**
   * 创建用户
   * @param userBO
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public Users createUser(UserBO userBO) {
    Users user;
    try {
      user = Users.UsersBuilder.anUsers()
              .withId(sid.nextShort())
              .withUsername(userBO.getUsername())
              .withPassword(MD5Utils.getMD5Str(userBO.getPassword()))
              .withNickname(userBO.getUsername()) // 默认用户昵称同用户名
              .withFace("") // 默认用户头像
              .withBirthday(DateUtil.stringToDate("1900-01-01")) // 默认生日
              .withSex(Sex.SECRET.getType()) // 默认性别 保密
              .withCreatedTime(new Date())
              .withUpdatedTime(new Date())
              .build();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    usersMapper.insert(user);
    return user;
  }


  /**
   * 根据用户名和密码匹配用户，用于登录
   * @param username
   * @param password
   * @return
   */
  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Users queryUserForLogin(String username, String password) {
    return usersMapper.queryUserForLogin(username, password);
  }
}
