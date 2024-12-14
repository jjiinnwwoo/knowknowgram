package com.api.knowknowgram.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.knowknowgram.common.annotation.CommonApiResponses;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {


    @GetMapping("/hello")
    @Operation(summary = "샘플 api", description = "샘클샘플")
    @CommonApiResponses
    public String hello() {
        return "hello";
    }
}
