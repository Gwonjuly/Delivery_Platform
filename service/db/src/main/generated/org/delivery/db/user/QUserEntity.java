package org.delivery.db.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -1823185807L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> registeredAt = createDateTime("registeredAt", java.time.LocalDateTime.class);

    public final ListPath<org.delivery.db.review.ReviewEntity, org.delivery.db.review.QReviewEntity> reviewEntityList = this.<org.delivery.db.review.ReviewEntity, org.delivery.db.review.QReviewEntity>createList("reviewEntityList", org.delivery.db.review.ReviewEntity.class, org.delivery.db.review.QReviewEntity.class, PathInits.DIRECT2);

    public final EnumPath<org.delivery.db.user.enums.UserStatus> status = createEnum("status", org.delivery.db.user.enums.UserStatus.class);

    public final DateTimePath<java.time.LocalDateTime> unregisteredAt = createDateTime("unregisteredAt", java.time.LocalDateTime.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

