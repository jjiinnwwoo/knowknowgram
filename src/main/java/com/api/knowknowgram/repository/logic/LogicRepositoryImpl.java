package com.api.knowknowgram.repository.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.api.knowknowgram.entity.QGameInfo;
import com.api.knowknowgram.entity.QLogic;
import com.api.knowknowgram.entity.QRecord;
import com.api.knowknowgram.entity.QReview;
import com.api.knowknowgram.payload.response.MyLogicResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
        QRecord record = QRecord.record;
        QReview review = QReview.review;

        // 메인 쿼리에서 직접 completionRate 계산
        List<MyLogicResponse> content = queryFactory
            .select(Projections.constructor(MyLogicResponse.class,
                logic.id,
                gameInfo.name,
                record.id.countDistinct(),
                review.id.countDistinct(),
                // 완료율 계산 (레코드가 없으면 0으로 설정, 있으면 완료된 레코드 수를 기준으로 계산)
                new CaseBuilder()
                    .when(record.id.count().eq(0L)) // 레코드 수가 0이면 완료율 0
                    .then(0L)
                    .otherwise(
                        new CaseBuilder()
                            .when(record.complete.isTrue()) // 완료된 레코드는 1, 아니면 0
                            .then(1L)
                            .otherwise(0L)
                            .sum()
                            .multiply(100L)
                            .divide(record.id.count()) // 완료된 레코드 수 / 전체 레코드 수
                    )
                    .as("completionRate"),
                new CaseBuilder()
                    .when(record.complete.isFalse())
                    .then(0)
                    .otherwise(
                        record.time.min()
                    ),
                logic.createDate
            ))
            .from(logic)
            .join(logic.gameInfo, gameInfo)
            .leftJoin(logic.records, record)
            .leftJoin(logic.reviews, review)
            .where(gameInfo.user.id.eq(userId))
            .groupBy(logic.id, gameInfo.name, logic.createDate)
            .orderBy(logic.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // Count query
        JPAQuery<Long> countQuery = queryFactory
            .select(logic.id.count())
            .from(logic)
            .join(logic.gameInfo, gameInfo)
            .where(gameInfo.user.id.eq(userId));

        // Page object
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
