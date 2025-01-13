package com.api.knowknowgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.knowknowgram.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findById(int id);
}
