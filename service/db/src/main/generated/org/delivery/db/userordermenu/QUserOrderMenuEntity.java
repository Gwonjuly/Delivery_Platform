package org.delivery.db.userordermenu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrderMenuEntity is a Querydsl query type for UserOrderMenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrderMenuEntity extends EntityPathBase<UserOrderMenuEntity> {

    private static final long serialVersionUID = 1114507705L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrderMenuEntity userOrderMenuEntity = new QUserOrderMenuEntity("userOrderMenuEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final EnumPath<org.delivery.db.userordermenu.enums.UserOrderMenuStatus> status = createEnum("status", org.delivery.db.userordermenu.enums.UserOrderMenuStatus.class);

    public final org.delivery.db.storemenu.QStoreMenuEntity storeMenu;

    public final org.delivery.db.userorder.QUserOrderEntity userOrder;

    public QUserOrderMenuEntity(String variable) {
        this(UserOrderMenuEntity.class, forVariable(variable), INITS);
    }

    public QUserOrderMenuEntity(Path<? extends UserOrderMenuEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrderMenuEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrderMenuEntity(PathMetadata metadata, PathInits inits) {
        this(UserOrderMenuEntity.class, metadata, inits);
    }

    public QUserOrderMenuEntity(Class<? extends UserOrderMenuEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.storeMenu = inits.isInitialized("storeMenu") ? new org.delivery.db.storemenu.QStoreMenuEntity(forProperty("storeMenu"), inits.get("storeMenu")) : null;
        this.userOrder = inits.isInitialized("userOrder") ? new org.delivery.db.userorder.QUserOrderEntity(forProperty("userOrder"), inits.get("userOrder")) : null;
    }

}

