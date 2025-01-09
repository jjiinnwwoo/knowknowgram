package com.api.knowknowgram.service;

import org.springframework.stereotype.Service;

import com.api.knowknowgram.repository.UserRecordRepository;

@Service
public class RecordService {
    private final UserRecordRepository UserRecordRepository;

    public RecordService(UserRecordRepository UserRecordRepository) {
        this.UserRecordRepository = UserRecordRepository;
    }

    public Long getCompletedGamesCount(Long userId) {
        return UserRecordRepository.countCompletedGamesByUserId(userId);
    }
}