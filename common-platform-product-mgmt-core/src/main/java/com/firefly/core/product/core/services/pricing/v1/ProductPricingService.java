package com.firefly.core.product.core.services.pricing.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductPricingService {

    /**
     * Retrieve a paginated list of all pricing records associated with the specified product.
     */
    Mono<PaginationResponse<ProductPricingDTO>> getAllPricings(UUID productId, PaginationRequest paginationRequest);

    /**
     * Create a new pricing record and associate it with a product.
     */
    Mono<ProductPricingDTO> createPricing(UUID productId, ProductPricingDTO productPricingDTO);

    /**
     * Retrieve a specific product pricing record by its unique identifier,
     * ensuring it matches the specified product.
     */
    Mono<ProductPricingDTO> getPricing(UUID productId, UUID pricingId);

    /**
     * Update an existing pricing record associated with the specified product.
     */
    Mono<ProductPricingDTO> updatePricing(UUID productId, UUID pricingId, ProductPricingDTO productPricingDTO);

    /**
     * Remove an existing pricing record from a product by its unique identifier.
     */
    Mono<Void> deletePricing(UUID productId, UUID pricingId);
}