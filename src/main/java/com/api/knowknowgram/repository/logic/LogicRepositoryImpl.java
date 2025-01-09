package com.api.knowknowgram.repository.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.api.knowknowgram.entity.QGameInfo;
import com.api.knowknowgram.entity.QLogic;
import com.api.knowknowgram.entity.QUserRecord;
import com.api.knowknowgram.entity.QReview;
import com.api.knowknowgram.payload.response.MyLogicResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class LogicRepositoryImpl implements LogicRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LogicRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<MyLogicResponse> findAllByUserId(Long userId, Pageable pageable) {
        QLogic logic = QLogic.logic;
        QGameInfo gameInfo = QGameInfo.gameInfo;
        QUserRecord userRecord = QUserRecord.userRecord;
        QReview review = QReview.review;
        
        List<MyLogicResponse> content = queryFactory
            .select(Projections.constructor(MyLogicResponse.class,
                logic.id,
                gameInfo.name,
                userRecord.id.countDistinct(),
                review.id.countDistinct(),                
                new CaseBuilder()
                    .when(userRecord.id.count().eq(0L))
                    .then(0L)
                    .otherwise(
                        new CaseBuilder()
                            .when(userRecord.complete.isTrue())
                            .then(1L)
                            .otherwise(0L)
                            .sum()
                            .multiply(100L)
                            .divide(userRecord.id.count())
                    )
                    .as("completionRate"),
                    Expressions.numberTemplate(Integer.class, "min({0})", 
                    new CaseBuilder()
                        .when(userRecord.complete.isTrue())
                        .then(userRecord.time)
                        .otherwise((Integer) null)
                )
                .as("shortestTime"),                
                logic.createDate
            ))
            .from(logic)
            .join(logic.gameInfo, gameInfo)
            .leftJoin(logic.userRecords, userRecord)
            .leftJoin(logic.reviews, review)
            .where(gameInfo.user.id.eq(userId))
            .groupBy(logic.id, gameInfo.name)
            .orderBy(logic.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(logic.id.count())
            .from(logic)
            .join(logic.gameInfo, gameInfo)
            .where(gameInfo.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
