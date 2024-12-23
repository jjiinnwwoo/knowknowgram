package com.api.knowknowgram.service;

import org.springframework.stereotype.Service;

import com.api.knowknowgram.repository.RecordRepository;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Long getCompletedGamesCount(Long userId) {
        return recordRepository.countCompletedGamesByUserId(userId);
    }
}