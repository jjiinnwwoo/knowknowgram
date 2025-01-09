package com.api.knowknowgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.knowknowgram.entity.UserRecord;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {

    @Query("SELECT COUNT(r) FROM UserRecord r WHERE r.user.id = :userId AND r.complete = true")
    Long countCompletedGamesByUserId(@Param("userId") Long userId);
}