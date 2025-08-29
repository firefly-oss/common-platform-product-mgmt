package com.firefly.core.product.core.services.lifecycle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import reactor.core.publisher.Mono;

public interface ProductLimitService {

    /**
     * Retrieve a paginated list of all limits associated with a specified product.
     */
    Mono<PaginationResponse<ProductLimitDTO>> getAllProductLimits(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new limit and associate it with a specified product.
     */
    Mono<ProductLimitDTO> createProductLimit(Long productId, ProductLimitDTO productLimitDTO);

    /**
     * Retrieve a specific product limit by its unique identifier (and possibly validate it belongs to the product).
     */
    Mono<ProductLimitDTO> getProductLimit(Long productId, Long limitId);

    /**
     * Update an existing product limit with new data.
     */
    Mono<ProductLimitDTO> updateProductLimit(Long productId, Long limitId, ProductLimitDTO productLimitDTO);

    /**
     * Remove an existing product limit by its unique identifier.
     */
    Mono<Void> deleteProductLimit(Long productId, Long limitId);
}