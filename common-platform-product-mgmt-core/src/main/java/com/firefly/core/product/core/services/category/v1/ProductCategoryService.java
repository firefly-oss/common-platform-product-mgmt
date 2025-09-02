package com.firefly.core.product.core.services.category.v1;

import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductCategoryService {

    /**
     * Retrieve a product category by its unique identifier.
     */
    Mono<ProductCategoryDTO> getById(UUID categoryId);

    /**
     * Create a new product category.
     */
    Mono<ProductCategoryDTO> create(ProductCategoryDTO categoryDTO);

    /**
     * Update an existing product category by its unique identifier.
     */
    Mono<ProductCategoryDTO> update(UUID categoryId, ProductCategoryDTO categoryDTO);

    /**
     * Delete a product category by its unique identifier.
     */
    Mono<Void> delete(UUID categoryId);
}

