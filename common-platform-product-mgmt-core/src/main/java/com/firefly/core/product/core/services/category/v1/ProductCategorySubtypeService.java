package com.firefly.core.product.core.services.category.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategorySubtypeDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductCategorySubtypeService {
    /**
     * Retrieve a paginated list of product subtypes associated with a category ID.
     */
    Mono<PaginationResponse<ProductCategorySubtypeDTO>> getAllByCategoryId(UUID categoryId, PaginationRequest paginationRequest);

    /**
     * Create a new product subtype under the specified category ID.
     */
    Mono<ProductCategorySubtypeDTO> create(UUID categoryId, ProductCategorySubtypeDTO subtypeRequest);

    /**
     * Retrieve a single product subtype by categoryId and subtypeId.
     */
    Mono<ProductCategorySubtypeDTO> getById(UUID categoryId, UUID subtypeId);

    /**
     * Update an existing product subtype by categoryId and subtypeId.
     */
    Mono<ProductCategorySubtypeDTO> update(UUID categoryId, UUID subtypeId, ProductCategorySubtypeDTO subtypeRequest);

    /**
     * Delete an existing product subtype by categoryId and subtypeId.
     */
    Mono<Void> delete(UUID categoryId, UUID subtypeId);
}
