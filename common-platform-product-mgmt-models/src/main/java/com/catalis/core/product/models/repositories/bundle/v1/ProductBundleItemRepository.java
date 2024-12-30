package com.catalis.core.product.models.repositories.bundle.v1;

import com.catalis.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductBundleItemRepository extends BaseRepository<ProductBundleItem, Long> {
    Flux<ProductBundleItem> findByProductBundleId(Long bundleId);
    Flux<ProductBundleItem> findByProductId(Long productId);

    // Find items in multiple bundles
    Flux<ProductBundleItem> findByProductBundleIdIn(Iterable<Long> bundleIds);

    // Pagination for bundle items
    Flux<ProductBundleItem> findByProductBundleId(Long bundleId, Pageable pageable);
    Mono<Long> countByProductBundleId(Long bundleId);

    Flux<ProductBundleItem> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}