package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import reactor.core.publisher.Mono;

public interface ProductPricingLocalizationService {

    /**
     * Retrieve a paginated list of all localization records associated with a given product pricing.
     */
    Mono<PaginationResponse<ProductPricingLocalizationDTO>> getAllLocalizations(
            Long pricingId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new localization record and associate it with the specified product pricing.
     */
    Mono<ProductPricingLocalizationDTO> createLocalization(
            Long pricingId,
            ProductPricingLocalizationDTO request
    );

    /**
     * Retrieve a specific localization record by its unique identifier.
     * (Optionally validate that it belongs to the correct pricing.)
     */
    Mono<ProductPricingLocalizationDTO> getLocalization(
            Long pricingId,
            Long localizationId
    );

    /**
     * Update an existing localization record for a product pricing.
     */
    Mono<ProductPricingLocalizationDTO> updateLocalization(
            Long pricingId,
            Long localizationId,
            ProductPricingLocalizationDTO request
    );

    /**
     * Remove an existing localization record by its unique identifier.
     */
    Mono<Void> deleteLocalization(Long pricingId, Long localizationId);
}