package com.firefly.core.product.models.repositories.category.v1;

import com.firefly.core.product.models.entities.category.v1.ProductCategory;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductCategoryRepository extends BaseRepository<ProductCategory, Long> {
    Flux<ProductCategory> findByParentCategoryId(Long parentId, Pageable pageable);
    Mono<ProductCategory> findByCategoryName(String name);
    Flux<ProductCategory> findByCategoryNameContainingIgnoreCase(String namePattern, Pageable pageable);
}