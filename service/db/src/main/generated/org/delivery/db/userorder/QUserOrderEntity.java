package org.delivery.db.userorder;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrderEntity is a Querydsl query type for UserOrderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrderEntity extends EntityPathBase<UserOrderEntity> {

    private static final long serialVersionUID = -2101411557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrderEntity userOrderEntity = new QUserOrderEntity("userOrderEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> acceptedAt = createDateTime("acceptedAt", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> cookingStartedAt = createDateTime("cookingStartedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deliveryStartedAt = createDateTime("deliveryStartedAt", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> orderedAt = createDateTime("orderedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> receivedAt = createDateTime("receivedAt", java.time.LocalDateTime.class);

    public final EnumPath<org.delivery.db.userorder.enums.UserOrderStatus> status = createEnum("status", org.delivery.db.userorder.enums.UserOrderStatus.class);

    public final org.delivery.db.store.QStoreEntity store;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final ListPath<org.delivery.db.userordermenu.UserOrderMenuEntity, org.delivery.db.userordermenu.QUserOrderMenuEntity> userOrderMenuList = this.<org.delivery.db.userordermenu.UserOrderMenuEntity, org.delivery.db.userordermenu.QUserOrderMenuEntity>createList("userOrderMenuList", org.delivery.db.userordermenu.UserOrderMenuEntity.class, org.delivery.db.userordermenu.QUserOrderMenuEntity.class, PathInits.DIRECT2);

    public QUserOrderEntity(String variable) {
        this(UserOrderEntity.class, forVariable(variable), INITS);
    }

    public QUserOrderEntity(Path<? extends UserOrderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrderEntity(PathMetadata metadata, PathInits inits) {
        this(UserOrderEntity.class, metadata, inits);
    }

    public QUserOrderEntity(Class<? extends UserOrderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new org.delivery.db.store.QStoreEntity(forProperty("store")) : null;
    }

}

