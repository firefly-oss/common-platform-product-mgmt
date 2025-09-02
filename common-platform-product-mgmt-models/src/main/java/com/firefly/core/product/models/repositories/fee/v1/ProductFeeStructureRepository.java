package com.firefly.core.product.models.repositories.fee.v1;

import com.firefly.core.product.models.entities.fee.v1.ProductFeeStructure;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProductFeeStructureRepository extends BaseRepository<ProductFeeStructure, UUID> {
    Flux<ProductFeeStructure> findByProductId(UUID productId);
    Flux<ProductFeeStructure> findByFeeStructureId(UUID feeStructureId);
    Flux<ProductFeeStructure> findByProductIdOrderByPriorityAsc(UUID productId);

    Flux<ProductFeeStructure> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);
}