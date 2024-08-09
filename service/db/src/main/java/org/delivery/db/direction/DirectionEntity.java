package org.delivery.db.direction;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DirectionEntity extends BaseEntity {

    //유저 관련 주소
    private String inputAddress;

    private double inputLatitude;

    private double inputLongitude;

    //스토어 관련 주소
    private String targetStoreName;

    private String targetAddress;

    private double targetLatitude;

    private double targetLongitude;

    //유저<->스토어 거리
    private double distance;
}
