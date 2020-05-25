package com.icoding.service;

import com.icoding.pojo.Users;

public interface UsersService {
    Users getUsersById(int id);
    Users queryIsUserExists(String username);
}
