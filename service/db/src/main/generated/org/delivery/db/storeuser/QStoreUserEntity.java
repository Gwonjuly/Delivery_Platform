package org.delivery.db.storeuser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreUserEntity is a Querydsl query type for StoreUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreUserEntity extends EntityPathBase<StoreUserEntity> {

    private static final long serialVersionUID = -254485459L;

    public static final QStoreUserEntity storeUserEntity = new QStoreUserEntity("storeUserEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> registeredAt = createDateTime("registeredAt", java.time.LocalDateTime.class);

    public final EnumPath<org.delivery.db.storeuser.enums.StoreUserRole> role = createEnum("role", org.delivery.db.storeuser.enums.StoreUserRole.class);

    public final EnumPath<org.delivery.db.storeuser.enums.StoreUserStatus> status = createEnum("status", org.delivery.db.storeuser.enums.StoreUserStatus.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> unregisteredAt = createDateTime("unregisteredAt", java.time.LocalDateTime.class);

    public QStoreUserEntity(String variable) {
        super(StoreUserEntity.class, forVariable(variable));
    }

    public QStoreUserEntity(Path<? extends StoreUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreUserEntity(PathMetadata metadata) {
        super(StoreUserEntity.class, metadata);
    }

}

