package org.delivery.db.review;

import com.querydsl.core.types.dsl.SimpleExpression;
import org.delivery.db.review.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends
        JpaRepository<ReviewEntity, Long>,
        QuerydslPredicateExecutor<ReviewEntity>,
        QuerydslBinderCustomizer<QReviewEntity>

{
    @Override
    default void customize(QuerydslBindings bindings, QReviewEntity root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.id, root.store.id, root.user.id, root.userOrder.id);
        bindings.bind(root.id).first(((path, value) -> path.eq(value)));
        bindings.bind(root.store.id).first((SimpleExpression::eq));
        bindings.bind(root.user.id).first((SimpleExpression::eq));
        bindings.bind(root.userOrder.id).first((SimpleExpression::eq));
    }


    List<ReviewEntity> findAllByStoreIdOrderByStarDesc(Long storeId);

    List<ReviewEntity> findAllByUserIdOrderByIdDesc(Long userId);

   Optional<ReviewEntity> findFirstByUserOrderIdAndStatusOrderByIdDesc(Long userOrderId, ReviewStatus status);
}
