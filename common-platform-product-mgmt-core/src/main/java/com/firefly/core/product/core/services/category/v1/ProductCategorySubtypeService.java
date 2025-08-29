package com.firefly.core.product.core.services.category.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategorySubtypeDTO;
import reactor.core.publisher.Mono;

public interface ProductCategorySubtypeService {
    /**
     * Retrieve a paginated list of product subtypes associated with a category ID.
     */
    Mono<PaginationResponse<ProductCategorySubtypeDTO>> getAllByCategoryId(Long categoryId, PaginationRequest paginationRequest);

    /**
     * Create a new product subtype under the specified category ID.
     */
    Mono<ProductCategorySubtypeDTO> create(Long categoryId, ProductCategorySubtypeDTO subtypeRequest);

    /**
     * Retrieve a single product subtype by categoryId and subtypeId.
     */
    Mono<ProductCategorySubtypeDTO> getById(Long categoryId, Long subtypeId);

    /**
     * Update an existing product subtype by categoryId and subtypeId.
     */
    Mono<ProductCategorySubtypeDTO> update(Long categoryId, Long subtypeId, ProductCategorySubtypeDTO subtypeRequest);

    /**
     * Delete an existing product subtype by categoryId and subtypeId.
     */
    Mono<Void> delete(Long categoryId, Long subtypeId);
}
