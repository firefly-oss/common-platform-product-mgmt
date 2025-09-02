package com.firefly.core.product.models.repositories.version.v1;

import com.firefly.core.product.models.entities.version.v1.ProductVersion;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductVersionRepository extends BaseRepository<ProductVersion, UUID> {
    Flux<ProductVersion> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);
}
