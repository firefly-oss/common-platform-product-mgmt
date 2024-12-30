package com.catalis.core.product.models.repositories.fee.v1;

import com.catalis.core.product.models.entities.fee.v1.ProductFeeStructure;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductFeeStructureRepository extends BaseRepository<ProductFeeStructure, Long> {
    Flux<ProductFeeStructure> findByProductId(Long productId);
    Flux<ProductFeeStructure> findByFeeStructureId(Long feeStructureId);
    Flux<ProductFeeStructure> findByProductIdOrderByPriorityAsc(Long productId);

    Flux<ProductFeeStructure> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}