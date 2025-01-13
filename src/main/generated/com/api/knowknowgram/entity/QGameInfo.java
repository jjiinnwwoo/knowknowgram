package com.api.knowknowgram.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameInfo is a Querydsl query type for GameInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameInfo extends EntityPathBase<GameInfo> {

    private static final long serialVersionUID = -1663037781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameInfo gameInfo = new QGameInfo("gameInfo");

    public final com.api.knowknowgram.common.base.QBaseEntity _super = new com.api.knowknowgram.common.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final StringPath difficulty = createString("difficulty");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final QLogic logic;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final QUsers user;

    public final NumberPath<Integer> userCount = createNumber("userCount", Integer.class);

    public QGameInfo(String variable) {
        this(GameInfo.class, forVariable(variable), INITS);
    }

    public QGameInfo(Path<? extends GameInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameInfo(PathMetadata metadata, PathInits inits) {
        this(GameInfo.class, metadata, inits);
    }

    public QGameInfo(Class<? extends GameInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.logic = inits.isInitialized("logic") ? new QLogic(forProperty("logic"), inits.get("logic")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user")) : null;
    }

}

