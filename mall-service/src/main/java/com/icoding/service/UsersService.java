package com.icoding.service;

import com.icoding.bo.UserBO;
import com.icoding.pojo.Users;

public interface UsersService {
    Users getUsersById(int id);
    Users queryIsUserExists(String username);
    Users createUser(UserBO userBO);
    Users queryUserForLogin(String username, String password);
}
