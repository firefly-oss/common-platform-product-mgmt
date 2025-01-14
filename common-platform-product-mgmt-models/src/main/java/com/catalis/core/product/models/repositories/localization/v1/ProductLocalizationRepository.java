package com.catalis.core.product.models.repositories.localization.v1;

import com.catalis.core.product.models.entities.localization.v1.ProductLocalization;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductLocalizationRepository extends BaseRepository<ProductLocalization, Long> {
    Flux<ProductLocalization> findAllByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}
