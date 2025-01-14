package com.catalis.core.product.models.repositories.version.v1;

import com.catalis.core.product.models.entities.version.v1.ProductVersion;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductVersionRepository extends BaseRepository<ProductVersion, Long> {
    Flux<ProductVersion> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}
