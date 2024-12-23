package com.api.knowknowgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.knowknowgram.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT COUNT(r) FROM Record r WHERE r.user.id = :userId AND r.complete = true")
    Long countCompletedGamesByUserId(@Param("userId") Long userId);
}