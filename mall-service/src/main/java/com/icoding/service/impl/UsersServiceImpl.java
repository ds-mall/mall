package com.icoding.service.impl;

import com.icoding.bo.UpdatedUserBO;
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
              // 默认用户昵称同用户名
              .withNickname(userBO.getUsername())
              // 默认用户头像
              .withFace("")
              // 默认生日
              .withBirthday(DateUtil.stringToDate("1900-01-01"))
              // 默认性别 保密
              .withSex(Sex.SECRET.getType())
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

  /**
   * 根据userId获取用户信息
   * @param userId
   * @return
   */
  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Users queryUserInfo(String userId) {
    Users user = usersMapper.selectByPrimaryKey(userId);
    // 隐藏密码
    user.setPassword("");
    return user;
  }

  /**
   * 更新用户信息
   * @param userId
   * @param updatedUserBO
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateUserInfo(String userId, UpdatedUserBO updatedUserBO) {
    Users user = updatedUserBO.convertToPojo();
    user.setId(userId);
    usersMapper.updateUserInfo(user);
  }

  /**
   * 更新用户头像
   * @param userId
   * @param userFaceUrl
   */
  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateUserFace(String userId, String userFaceUrl) {
    usersMapper.updateUserFace(userId, userFaceUrl);
  }
}
