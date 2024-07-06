package org.delivery.db.storemenu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreMenuEntity is a Querydsl query type for StoreMenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreMenuEntity extends EntityPathBase<StoreMenuEntity> {

    private static final long serialVersionUID = -685307819L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreMenuEntity storeMenuEntity = new QStoreMenuEntity("storeMenuEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final EnumPath<org.delivery.db.storemenu.enums.StoreMenuStatus> status = createEnum("status", org.delivery.db.storemenu.enums.StoreMenuStatus.class);

    public final org.delivery.db.store.QStoreEntity store;

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public QStoreMenuEntity(String variable) {
        this(StoreMenuEntity.class, forVariable(variable), INITS);
    }

    public QStoreMenuEntity(Path<? extends StoreMenuEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreMenuEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreMenuEntity(PathMetadata metadata, PathInits inits) {
        this(StoreMenuEntity.class, metadata, inits);
    }

    public QStoreMenuEntity(Class<? extends StoreMenuEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new org.delivery.db.store.QStoreEntity(forProperty("store")) : null;
    }

}

