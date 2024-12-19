package com.api.knowknowgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.knowknowgram.entity.Logic;

@Repository
public interface LogicRepository extends JpaRepository<Logic, Integer>, JpaSpecificationExecutor<Logic> {
}