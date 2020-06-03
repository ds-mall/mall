package com.icoding.service;

import com.icoding.bo.UserAddressBO;
import com.icoding.pojo.UserAddress;
import com.icoding.vo.UserAddressVO;

import java.util.List;

public interface AddressService {
  /**
   * 查询指定用户的地址列表
   * @param userId
   * @return
   */
  List<UserAddressVO> getAddressList(String userId);

  /**
   * 新增用户地址
   * @param userAddressBO
   */
  void add(UserAddressBO userAddressBO);

  /**
   * 删除指定用户地址
   * @param userId
   * @param addressId
   */
  void del(String userId, String addressId);

  /**
   * 修改用户地址
   * @param userAddressBO
   */
  void update(UserAddressBO userAddressBO);

  /**
   * 设置地址为默认地址
   * @param userId
   * @param addressId
   */
  void setDefault(String userId, String addressId);

}
