package org.delivery.db.direction;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDirectionEntity is a Querydsl query type for DirectionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectionEntity extends EntityPathBase<DirectionEntity> {

    private static final long serialVersionUID = 336324211L;

    public static final QDirectionEntity directionEntity = new QDirectionEntity("directionEntity");

    public final org.delivery.db.QBaseEntity _super = new org.delivery.db.QBaseEntity(this);

    public final NumberPath<Double> distance = createNumber("distance", Double.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath inputAddress = createString("inputAddress");

    public final NumberPath<Double> inputLatitude = createNumber("inputLatitude", Double.class);

    public final NumberPath<Double> inputLongitude = createNumber("inputLongitude", Double.class);

    public final StringPath targetAddress = createString("targetAddress");

    public final NumberPath<Double> targetLatitude = createNumber("targetLatitude", Double.class);

    public final NumberPath<Double> targetLongitude = createNumber("targetLongitude", Double.class);

    public final StringPath targetStoreName = createString("targetStoreName");

    public QDirectionEntity(String variable) {
        super(DirectionEntity.class, forVariable(variable));
    }

    public QDirectionEntity(Path<? extends DirectionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDirectionEntity(PathMetadata metadata) {
        super(DirectionEntity.class, metadata);
    }

}

