package com.icoding.service.impl;

import com.icoding.bo.UserAddressBO;
import com.icoding.mapper.UserAddressMapper;
import com.icoding.pojo.UserAddress;
import com.icoding.service.AddressService;
import com.icoding.vo.UserAddressVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  UserAddressMapper userAddressMapper;

  @Autowired
  Sid sid;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<UserAddressVO> getAddressList(String userId) {
    return userAddressMapper.getAddressList(userId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void add(UserAddressBO userAddressBO) {
    // 0 查看当前用户是否存在地址
    int isDefault = 0;
    List<UserAddressVO> addressList = this.getAddressList(userAddressBO.getUserId());
    // 如果不存在地址，则当前新增地址设置为默认地址
    if (addressList.size() == 0) {
      isDefault = 1;
    }

    // 1 BO --> PoJo
    String shotId = sid.nextShort();
    UserAddress userAddress = userAddressBO.convertToPoJo();
    userAddress.setId(shotId);
    userAddress.setIsDefault(isDefault);

    // 2 执行新增
    userAddressMapper.addNewAddress(userAddress);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void del(String userId, String addressId) {
    userAddressMapper.delAddress(userId, addressId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void update(UserAddressBO userAddressBO) {
    // 1 BO --> PoJo
    UserAddress userAddress = userAddressBO.convertToPoJo();

    // 2 执行新增
    userAddressMapper.updateAddress(userAddress);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void setDefault(String userId, String addressId) {
    // 1 将当前默认地址设置为非默认
    userAddressMapper.clearDefault();

    // 2 将指定地址设置为默认地址
    userAddressMapper.setDefault(userId, addressId);
  }
}
