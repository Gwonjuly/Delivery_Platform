package org.delivery.db.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreEntity is a Querydsl query type for StoreEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreEntity extends EntityPathBase<StoreEntity> {

    private static final long serialVersionUID = -231942857L;

    public static final QStoreEntity storeEntity = new QStoreEntity("storeEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final EnumPath<org.delivery.db.store.enums.StoreCategory> category = createEnum("category", org.delivery.db.store.enums.StoreCategory.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<java.math.BigDecimal> minimumAmount = createNumber("minimumAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> minimumDeliveryAmount = createNumber("minimumDeliveryAmount", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<org.delivery.db.review.ReviewEntity, org.delivery.db.review.QReviewEntity> reviewEntityList = this.<org.delivery.db.review.ReviewEntity, org.delivery.db.review.QReviewEntity>createList("reviewEntityList", org.delivery.db.review.ReviewEntity.class, org.delivery.db.review.QReviewEntity.class, PathInits.DIRECT2);

    public final NumberPath<Double> star = createNumber("star", Double.class);

    public final EnumPath<org.delivery.db.store.enums.StoreStatus> status = createEnum("status", org.delivery.db.store.enums.StoreStatus.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public QStoreEntity(String variable) {
        super(StoreEntity.class, forVariable(variable));
    }

    public QStoreEntity(Path<? extends StoreEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreEntity(PathMetadata metadata) {
        super(StoreEntity.class, metadata);
    }

}

