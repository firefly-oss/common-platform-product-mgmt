package com.firefly.core.product.core.services.localization.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.localization.v1.ProductLocalizationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductLocalizationService {

    /**
     * Retrieve a paginated list of localizations for a specific product.
     */
    Mono<PaginationResponse<ProductLocalizationDTO>> getAllLocalizations(
            UUID productId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new localization for a specific product.
     */
    Mono<ProductLocalizationDTO> createLocalization(UUID productId, ProductLocalizationDTO localizationDTO);

    /**
     * Retrieve a specific localization by its unique ID, ensuring it belongs to the specified product.
     */
    Mono<ProductLocalizationDTO> getLocalizationById(UUID productId, UUID localizationId);

    /**
     * Update an existing localization for a specific product.
     */
    Mono<ProductLocalizationDTO> updateLocalization(UUID productId, UUID localizationId, ProductLocalizationDTO localizationDTO);

    /**
     * Delete an existing product localization by its unique ID.
     */
    Mono<Void> deleteLocalization(UUID productId, UUID localizationId);
}
