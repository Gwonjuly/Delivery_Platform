package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    //유효한 스토어
    //select * from store where id =? and status='REGISTERED' order by id desc limit , id 최신 것부터
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    //유효한 스토어 리스트, id순 정렬
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    //유요한 특정 카테고리 스토어 리스트, 좋아요 순 정렬
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);
}
