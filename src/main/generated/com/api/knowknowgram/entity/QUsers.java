package com.api.knowknowgram.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 294533885L;

    public static final QUsers users = new QUsers("users");

    public final com.api.knowknowgram.common.base.QBaseEntity _super = new com.api.knowknowgram.common.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath photo = createString("photo");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final ListPath<UserRecord, QUserRecord> records = this.<UserRecord, QUserRecord>createList("records", UserRecord.class, QUserRecord.class, PathInits.DIRECT2);

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

