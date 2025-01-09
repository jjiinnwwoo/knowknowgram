package com.api.knowknowgram.repository.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.knowknowgram.entity.Logic;

@Repository
public interface LogicRepository extends JpaRepository<Logic, Integer>, LogicRepositoryCustom {
        // 로직 ID로 찾기
        Optional<Logic> findById(Long id);
        
        // 전체 조회 (인기순)
        @Query("SELECT l FROM Logic l JOIN l.gameInfo g " +
                "LEFT JOIN UserRecord r ON r.logic.id = l.id AND r.user.id = :userId " +
                "LEFT JOIN Likes li ON li.logic.id = l.id AND li.user.id = :userId " +
                "ORDER BY g.likeCount DESC")
        Page<Logic> findAllByOrderByLikeCountDesc(@Param("userId") Long userId, Pageable pageable);

        // 전체 조회 (등록일순)
        @Query("SELECT DISTINCT l FROM Logic l " +
                "LEFT JOIN l.userRecords r ON r.logic.id = l.id AND r.user.id = :userId " +
                "LEFT JOIN l.likes li ON li.logic.id = l.id AND li.user.id = :userId " +
                "ORDER BY l.createDate DESC")
        Page<Logic> findAllByOrderByCreateDateDesc(@Param("userId") Long userId, Pageable pageable);

        // 전체 조회 (칸수별 조회)
        @Query("SELECT l FROM Logic l LEFT JOIN UserRecord r ON r.logic.id = l.id AND r.user.id = :userId " +
                "LEFT JOIN Likes li ON li.logic.id = l.id AND li.user.id = :userId " +
                "WHERE l.rowsNum = :rowsNum AND l.colsNum = :colsNum")
        Page<Logic> findAllByRowsNumAndColsNum(@Param("userId") Long userId, @Param("rowsNum") int rowsNum, @Param("colsNum") int colsNum, Pageable pageable);
        
        // @EntityGraph(attributePaths = {"userRecords"})
        // @Query("SELECT l FROM Logic l " +
        //         "WHERE l.rowsNum = :rowsNum AND l.colsNum = :colsNum")
        // Page<Logic> findAllByRowsNumAndColsNum(@Param("rowsNum") int rowsNum, @Param("colsNum") int colsNum, Pageable pageable);
}