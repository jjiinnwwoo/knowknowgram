package com.api.knowknowgram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.knowknowgram.service.RecordService;

@RestController
@RequestMapping("/api/records")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/completed-count")
    public ResponseEntity<Long> getCompletedGamesCount(@RequestParam Long userId) {
        Long count = recordService.getCompletedGamesCount(userId);
        return ResponseEntity.ok(count);
    }
}