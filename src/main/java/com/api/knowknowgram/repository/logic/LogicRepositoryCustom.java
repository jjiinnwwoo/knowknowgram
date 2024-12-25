package com.api.knowknowgram.repository.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.knowknowgram.payload.response.MyLogicResponse;

public interface LogicRepositoryCustom {
    Page<MyLogicResponse> findAllByUserId(Long userId, Pageable pageable);
}

