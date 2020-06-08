package com.icoding.service;

import com.icoding.bo.UpdatedUserBO;
import com.icoding.bo.UserBO;
import com.icoding.pojo.Users;

public interface UsersService {
    Users getUsersById(int id);
    Users queryIsUserExists(String username);
    Users createUser(UserBO userBO);
    Users queryUserForLogin(String username, String password);

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 根据userId更新用户信息
     * @param userId
     * @param updatedUserBO
     */
    void updateUserInfo(String userId, UpdatedUserBO updatedUserBO);
}
