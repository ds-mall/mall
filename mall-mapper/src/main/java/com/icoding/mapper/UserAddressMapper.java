package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.UserAddress;
import com.icoding.vo.UserAddressVO;

import java.util.List;

public interface UserAddressMapper extends MyMapper<UserAddress> {
  List<UserAddressVO> getAddressList(String userId);
  void addNewAddress(UserAddress userAddress);
  void delAddress(String userId, String addressId);
  void updateAddress(UserAddress userAddress);
  void clearDefault();
  void setDefault(String userId, String addressId);
}
