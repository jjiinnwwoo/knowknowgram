package com.api.knowknowgram.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLogic is a Querydsl query type for Logic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLogic extends EntityPathBase<Logic> {

    private static final long serialVersionUID = 286104659L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLogic logic = new QLogic("logic");

    public final com.api.knowknowgram.common.base.QBaseEntity _super = new com.api.knowknowgram.common.base.QBaseEntity(this);

    public final StringPath colHints = createString("colHints");

    public final NumberPath<Integer> colsNum = createNumber("colsNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final QGameInfo gameInfo;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Record, QRecord> records = this.<Record, QRecord>createList("records", Record.class, QRecord.class, PathInits.DIRECT2);

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath rowHints = createString("rowHints");

    public final NumberPath<Integer> rowsNum = createNumber("rowsNum", Integer.class);

    public final StringPath solution = createString("solution");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QLogic(String variable) {
        this(Logic.class, forVariable(variable), INITS);
    }

    public QLogic(Path<? extends Logic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLogic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLogic(PathMetadata metadata, PathInits inits) {
        this(Logic.class, metadata, inits);
    }

    public QLogic(Class<? extends Logic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameInfo = inits.isInitialized("gameInfo") ? new QGameInfo(forProperty("gameInfo"), inits.get("gameInfo")) : null;
    }

}

