package com.api.knowknowgram.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRecord is a Querydsl query type for UserRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRecord extends EntityPathBase<UserRecord> {

    private static final long serialVersionUID = 762192935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRecord userRecord = new QUserRecord("userRecord");

    public final com.api.knowknowgram.common.base.QBaseEntity _super = new com.api.knowknowgram.common.base.QBaseEntity(this);

    public final BooleanPath complete = createBoolean("complete");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLogic logic;

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final QUsers user;

    public QUserRecord(String variable) {
        this(UserRecord.class, forVariable(variable), INITS);
    }

    public QUserRecord(Path<? extends UserRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRecord(PathMetadata metadata, PathInits inits) {
        this(UserRecord.class, metadata, inits);
    }

    public QUserRecord(Class<? extends UserRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.logic = inits.isInitialized("logic") ? new QLogic(forProperty("logic"), inits.get("logic")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user"), inits.get("user")) : null;
    }

}

