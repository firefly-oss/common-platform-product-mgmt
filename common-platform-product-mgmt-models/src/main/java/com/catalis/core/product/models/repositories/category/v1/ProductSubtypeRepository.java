package com.catalis.core.product.models.repositories.category.v1;

import com.catalis.core.product.models.entities.category.v1.ProductSubtype;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductSubtypeRepository extends BaseRepository<ProductSubtype, Long> {
    Flux<ProductSubtype> findByProductCategoryId(Long categoryId);
    Mono<ProductSubtype> findBySubtypeName(String name);
    Flux<ProductSubtype> findBySubtypeNameContainingIgnoreCase(String namePattern);
}