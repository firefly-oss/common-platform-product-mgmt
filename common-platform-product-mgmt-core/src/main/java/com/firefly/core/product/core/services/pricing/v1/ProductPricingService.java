package com.firefly.core.product.core.services.pricing.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import reactor.core.publisher.Mono;

public interface ProductPricingService {

    /**
     * Retrieve a paginated list of all pricing records associated with the specified product.
     */
    Mono<PaginationResponse<ProductPricingDTO>> getAllPricings(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new pricing record and associate it with a product.
     */
    Mono<ProductPricingDTO> createPricing(Long productId, ProductPricingDTO productPricingDTO);

    /**
     * Retrieve a specific product pricing record by its unique identifier,
     * ensuring it matches the specified product.
     */
    Mono<ProductPricingDTO> getPricing(Long productId, Long pricingId);

    /**
     * Update an existing pricing record associated with the specified product.
     */
    Mono<ProductPricingDTO> updatePricing(Long productId, Long pricingId, ProductPricingDTO productPricingDTO);

    /**
     * Remove an existing pricing record from a product by its unique identifier.
     */
    Mono<Void> deletePricing(Long productId, Long pricingId);
}