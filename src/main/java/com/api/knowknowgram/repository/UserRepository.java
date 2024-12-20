package com.api.knowknowgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.knowknowgram.entity.Users;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    public Optional<Users> findById(int id);
    public Optional<Users> findByEmail(String email);
    public Optional<Users> findByNickname(String nickname);
}
