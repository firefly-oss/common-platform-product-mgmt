package com.firefly.core.product.core.services.category.v1;

import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import reactor.core.publisher.Mono;

public interface ProductCategoryService {

    /**
     * Retrieve a product category by its unique identifier.
     */
    Mono<ProductCategoryDTO> getById(Long categoryId);

    /**
     * Create a new product category.
     */
    Mono<ProductCategoryDTO> create(ProductCategoryDTO categoryDTO);

    /**
     * Update an existing product category by its unique identifier.
     */
    Mono<ProductCategoryDTO> update(Long categoryId, ProductCategoryDTO categoryDTO);

    /**
     * Delete a product category by its unique identifier.
     */
    Mono<Void> delete(Long categoryId);
}

