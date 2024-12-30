package com.catalis.core.product.models.repositories.lifecycle.v1;

import com.catalis.core.product.interfaces.enums.lifecycle.v1.LifecycleStatusEnum;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ProductLifecycleRepository extends BaseRepository<ProductLifecycle, Long> {
    Flux<ProductLifecycle> findByProductId(Long productId);
    Flux<ProductLifecycle> findByLifecycleStatus(LifecycleStatusEnum status);
    Flux<ProductLifecycle> findByStatusStartDateBetween(LocalDateTime start, LocalDateTime end);

    Flux<ProductLifecycle> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);

    Mono<ProductLifecycle> findFirstByProductIdOrderByStatusStartDateDesc(Long productId);
}