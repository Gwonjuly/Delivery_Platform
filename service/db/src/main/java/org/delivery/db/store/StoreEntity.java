package org.delivery.db.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.sound.sampled.spi.AudioFileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder//부모의 entity(BaseEntity)도 빌더에 포함
@EqualsAndHashCode(callSuper = true)//callSuper=true: 부모의 entity를 포함해서 비교, false면 해당 entity에서 비교
@Entity
//@EntityListeners(AudioFileReader.class)
@Table(name="store")
public class StoreEntity extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private double star;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(precision = 11, scale = 4,nullable = false)//DECIMAL(1,4)
    private BigDecimal minimumAmount;

    @Column(precision = 11, scale = 4,nullable = false)//DECIMAL(1,4)
    private BigDecimal minimumDeliveryAmount;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private double latitude;//Y 좌표값, 경위도인 경우 위도(latitude)

    @Column(nullable = false)
    private double longitude;//X 좌표값, 경위도인 경우 경도(longitude)

    /*@CreatedDate
    @Column(updatable = false)
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime modifiedDate;*/

    @OneToMany(mappedBy = "store")
    @ToString.Exclude
    @JsonIgnore
    @OrderBy("star")
    private List<ReviewEntity> reviewEntityList;

}
