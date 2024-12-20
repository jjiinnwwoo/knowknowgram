package com.api.knowknowgram.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.knowknowgram.common.exception.NotFoundException;
import com.api.knowknowgram.common.response.ResponseCode;
import com.api.knowknowgram.common.security.UserDetailsImpl;
import com.api.knowknowgram.dto.UsersDto;
import com.api.knowknowgram.entity.Users;
import com.api.knowknowgram.repository.UserRepository;
import com.api.knowknowgram.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UsersDto getUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(ResponseCode.USER_NOT_FOUND.getMessage()));

        return UsersDto.fromEntity(user);
    }

    @Override
    @Transactional
    public UsersDto getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ResponseCode.USER_NOT_FOUND.getMessage()));

        return UsersDto.fromEntity(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    
        return UserDetailsImpl.build(user);
    }    
}
