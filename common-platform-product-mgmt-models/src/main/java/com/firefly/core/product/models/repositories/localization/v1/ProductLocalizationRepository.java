package com.firefly.core.product.models.repositories.localization.v1;

import com.firefly.core.product.models.entities.localization.v1.ProductLocalization;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductLocalizationRepository extends BaseRepository<ProductLocalization, UUID> {
    Flux<ProductLocalization> findAllByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);
}
