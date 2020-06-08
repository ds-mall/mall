package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Users;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper extends MyMapper<Users> {
  Users getUserByUsername(String username);
  Users queryUserForLogin(String username, String password);
  void updateUserInfo(@Param("user") Users user);
}
