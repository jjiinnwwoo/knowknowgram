package com.api.knowknowgram.payload.response;

import lombok.Data;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaginatedResponse<T> {    
    // 데이터 리스트
    private List<T> content;     

    // 현재 페이지 번호
    @JsonProperty("current_page")
    private int currentPage;     

    // 총 페이지 수
    @JsonProperty("total_pages")
    private int totalPages;      

    // 총 데이터 개수
    @JsonProperty("total_elements")
    private long totalElements;  
    
    // 마지막 페이지 여부
    @JsonProperty("is_last")
    private boolean isLast;      

    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber() + 1; // 1-based로 변경
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.isLast = page.isLast();
    }
}
