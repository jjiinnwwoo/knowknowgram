package com.api.knowknowgram.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.knowknowgram.entity.Logic;

@Repository
public interface LogicRepository extends JpaRepository<Logic, Integer>, JpaSpecificationExecutor<Logic> {    
        // 로직 ID로 찾기
        List<Logic> findById(Long id);
        
        // 인기순
        @Query("SELECT l FROM Logic l JOIN FETCH l.gameInfo g " +
        "LEFT JOIN Record r ON r.logic.id = l.id AND r.user.id = :userId " +
        "ORDER BY g.likeCount DESC")
        Page<Logic> findAllByOrderByLikeCountDesc(@Param("userId") Long userId, Pageable pageable);

        // 등록일순
        @Query("SELECT l FROM Logic l LEFT JOIN FETCH Record r ON r.logic.id = l.id AND r.user.id = :userId " +
                "ORDER BY l.createDate DESC")
        List<Logic> findAllByOrderByCreateDateDesc(@Param("userId") Long userId, Pageable pageable);

        // 칸수별 조회
        @Query("SELECT l FROM Logic l LEFT JOIN FETCH Record r ON r.logic.id = l.id AND r.user.id = :userId " +
                "WHERE l.rowsNum = :rowsNum AND l.colsNum = :colsNum")
        List<Logic> findAllByRowsNumAndColsNum(@Param("userId") Long userId, 
                                                @Param("rowsNum") int rowsNum, 
                                                @Param("colsNum") int colsNum, 
                                                Pageable pageable);
}