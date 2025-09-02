package com.firefly.core.product.core.services.feature.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductFeatureService {

    /**
     * Retrieve a paginated list of all features associated with the specified product.
     */
    Mono<PaginationResponse<ProductFeatureDTO>> getAllFeatures(UUID productId, PaginationRequest paginationRequest);

    /**
     * Create a new feature linked to the specified product.
     */
    Mono<ProductFeatureDTO> createFeature(UUID productId, ProductFeatureDTO featureDTO);

    /**
     * Retrieve a specific product feature by its unique identifier, ensuring it matches the product.
     */
    Mono<ProductFeatureDTO> getFeature(UUID productId, UUID featureId);

    /**
     * Update an existing feature associated with the specified product.
     */
    Mono<ProductFeatureDTO> updateFeature(UUID productId, UUID featureId, ProductFeatureDTO featureDTO);

    /**
     * Remove an existing feature from the specified product.
     */
    Mono<Void> deleteFeature(UUID productId, UUID featureId);
}