package com.firefly.core.product.core.services.feature.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import reactor.core.publisher.Mono;

public interface ProductFeatureService {

    /**
     * Retrieve a paginated list of all features associated with the specified product.
     */
    Mono<PaginationResponse<ProductFeatureDTO>> getAllFeatures(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new feature linked to the specified product.
     */
    Mono<ProductFeatureDTO> createFeature(Long productId, ProductFeatureDTO featureDTO);

    /**
     * Retrieve a specific product feature by its unique identifier, ensuring it matches the product.
     */
    Mono<ProductFeatureDTO> getFeature(Long productId, Long featureId);

    /**
     * Update an existing feature associated with the specified product.
     */
    Mono<ProductFeatureDTO> updateFeature(Long productId, Long featureId, ProductFeatureDTO featureDTO);

    /**
     * Remove an existing feature from the specified product.
     */
    Mono<Void> deleteFeature(Long productId, Long featureId);
}