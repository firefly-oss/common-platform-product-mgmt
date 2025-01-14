package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import reactor.core.publisher.Mono;

public interface ProductLifecycleService {

    /**
     * Retrieve a paginated list of all lifecycle entries associated with a given product.
     */
    Mono<PaginationResponse<ProductLifecycleDTO>> getProductLifecycles(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new lifecycle entry for a specific product.
     */
    Mono<ProductLifecycleDTO> createProductLifecycle(Long productId, ProductLifecycleDTO request);

    /**
     * Retrieve a specific lifecycle entry by its unique identifier, ensuring it belongs to the specified product.
     */
    Mono<ProductLifecycleDTO> getProductLifecycle(Long productId, Long lifecycleId);

    /**
     * Update an existing lifecycle entry associated with the specified product.
     */
    Mono<ProductLifecycleDTO> updateProductLifecycle(Long productId, Long lifecycleId, ProductLifecycleDTO request);

    /**
     * Delete an existing lifecycle entry from a product by its unique identifier.
     */
    Mono<Void> deleteProductLifecycle(Long productId, Long lifecycleId);
}