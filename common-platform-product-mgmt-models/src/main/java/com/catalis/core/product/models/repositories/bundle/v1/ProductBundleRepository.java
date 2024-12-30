package com.catalis.core.product.models.repositories.bundle.v1;

import com.catalis.core.product.interfaces.enums.bundle.v1.BundleStatusEnum;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundle;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductBundleRepository extends BaseRepository<ProductBundle, Long> {
    Flux<ProductBundle> findByBundleStatus(BundleStatusEnum status);
    Flux<ProductBundle> findByBundleNameContainingIgnoreCase(String namePattern);

    Flux<ProductBundle> findByBundleStatus(BundleStatusEnum status, Pageable pageable);
    Mono<Long> countByBundleStatus(BundleStatusEnum status);
}