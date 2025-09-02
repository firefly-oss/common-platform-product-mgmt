package com.firefly.core.product.models.repositories.bundle.v1;

import com.firefly.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProductBundleItemRepository extends BaseRepository<ProductBundleItem, UUID> {
    Flux<ProductBundleItem> findByProductBundleId(UUID bundleId);
    Flux<ProductBundleItem> findByProductId(UUID productId);

    // Pagination for bundle items
    Flux<ProductBundleItem> findByProductBundleId(UUID bundleId, Pageable pageable);
    Mono<Long> countByProductBundleId(UUID bundleId);

    Flux<ProductBundleItem> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);

}