package com.icoding.mapper;

import com.icoding.bo.UserBO;
import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

public interface UsersMapper extends MyMapper<Users> {
  Users getUserByUsername(String username);
}
