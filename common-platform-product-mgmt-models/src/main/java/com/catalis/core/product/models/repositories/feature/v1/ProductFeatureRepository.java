package com.catalis.core.product.models.repositories.feature.v1;

import com.catalis.core.product.interfaces.enums.feature.v1.FeatureTypeEnum;
import com.catalis.core.product.models.entities.feature.v1.ProductFeature;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductFeatureRepository extends BaseRepository<ProductFeature, Long> {
    Flux<ProductFeature> findByProductId(Long productId);
    Flux<ProductFeature> findByFeatureType(FeatureTypeEnum type);
    Flux<ProductFeature> findByIsMandatory(Boolean isMandatory);

    Flux<ProductFeature> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);

    Flux<ProductFeature> findByFeatureType(FeatureTypeEnum type, Pageable pageable);
    Mono<Long> countByFeatureType(FeatureTypeEnum type);
}