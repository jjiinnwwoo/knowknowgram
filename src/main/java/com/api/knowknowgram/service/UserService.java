package com.api.knowknowgram.service;

import com.api.knowknowgram.dto.UsersDto;

public interface UserService {
    UsersDto getUserById(Long id);
    UsersDto getUserByEmail(String Email);
}
