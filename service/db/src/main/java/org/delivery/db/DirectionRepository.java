package org.delivery.db;

import org.delivery.db.direction.DirectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<DirectionEntity, Long>{
}
