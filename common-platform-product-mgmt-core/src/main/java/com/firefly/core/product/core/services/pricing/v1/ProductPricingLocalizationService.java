package com.firefly.core.product.core.services.pricing.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductPricingLocalizationService {

    /**
     * Retrieve a paginated list of all localization records associated with a given product pricing.
     */
    Mono<PaginationResponse<ProductPricingLocalizationDTO>> getAllLocalizations(
            UUID pricingId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new localization record and associate it with the specified product pricing.
     */
    Mono<ProductPricingLocalizationDTO> createLocalization(
            UUID pricingId,
            ProductPricingLocalizationDTO request
    );

    /**
     * Retrieve a specific localization record by its unique identifier.
     * (Optionally validate that it belongs to the correct pricing.)
     */
    Mono<ProductPricingLocalizationDTO> getLocalization(
            UUID pricingId,
            UUID localizationId
    );

    /**
     * Update an existing localization record for a product pricing.
     */
    Mono<ProductPricingLocalizationDTO> updateLocalization(
            UUID pricingId,
            UUID localizationId,
            ProductPricingLocalizationDTO request
    );

    /**
     * Remove an existing localization record by its unique identifier.
     */
    Mono<Void> deleteLocalization(UUID pricingId, UUID localizationId);
}