package com.firefly.core.product.core.services.lifecycle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductLifecycleService {

    /**
     * Retrieve a paginated list of all lifecycle entries associated with a given product.
     */
    Mono<PaginationResponse<ProductLifecycleDTO>> getProductLifecycles(UUID productId, PaginationRequest paginationRequest);

    /**
     * Create a new lifecycle entry for a specific product.
     */
    Mono<ProductLifecycleDTO> createProductLifecycle(UUID productId, ProductLifecycleDTO request);

    /**
     * Retrieve a specific lifecycle entry by its unique identifier, ensuring it belongs to the specified product.
     */
    Mono<ProductLifecycleDTO> getProductLifecycle(UUID productId, UUID lifecycleId);

    /**
     * Update an existing lifecycle entry associated with the specified product.
     */
    Mono<ProductLifecycleDTO> updateProductLifecycle(UUID productId, UUID lifecycleId, ProductLifecycleDTO request);

    /**
     * Delete an existing lifecycle entry from a product by its unique identifier.
     */
    Mono<Void> deleteProductLifecycle(UUID productId, UUID lifecycleId);
}