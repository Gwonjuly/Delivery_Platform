package org.delivery.db.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewEntity is a Querydsl query type for ReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEntity extends EntityPathBase<ReviewEntity> {

    private static final long serialVersionUID = -773236975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewEntity reviewEntity = new QReviewEntity("reviewEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> replyCreatedAt = createDateTime("replyCreatedAt", java.time.LocalDateTime.class);

    public final StringPath replyText = createString("replyText");

    public final DateTimePath<java.time.LocalDateTime> replyUpdatedAt = createDateTime("replyUpdatedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> reviewCreatedAt = createDateTime("reviewCreatedAt", java.time.LocalDateTime.class);

    public final StringPath reviewText = createString("reviewText");

    public final DateTimePath<java.time.LocalDateTime> reviewUpdatedAt = createDateTime("reviewUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> star = createNumber("star", Double.class);

    public final EnumPath<org.delivery.db.review.enums.ReviewStatus> status = createEnum("status", org.delivery.db.review.enums.ReviewStatus.class);

    public final org.delivery.db.store.QStoreEntity store;

    public final org.delivery.db.user.QUserEntity user;

    public final org.delivery.db.userorder.QUserOrderEntity userOrder;

    public QReviewEntity(String variable) {
        this(ReviewEntity.class, forVariable(variable), INITS);
    }

    public QReviewEntity(Path<? extends ReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewEntity(PathMetadata metadata, PathInits inits) {
        this(ReviewEntity.class, metadata, inits);
    }

    public QReviewEntity(Class<? extends ReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new org.delivery.db.store.QStoreEntity(forProperty("store")) : null;
        this.user = inits.isInitialized("user") ? new org.delivery.db.user.QUserEntity(forProperty("user")) : null;
        this.userOrder = inits.isInitialized("userOrder") ? new org.delivery.db.userorder.QUserOrderEntity(forProperty("userOrder"), inits.get("userOrder")) : null;
    }

}

