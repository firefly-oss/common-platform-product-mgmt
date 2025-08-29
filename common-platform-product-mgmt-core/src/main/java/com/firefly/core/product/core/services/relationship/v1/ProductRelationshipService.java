package com.firefly.core.product.core.services.relationship.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
import reactor.core.publisher.Mono;

public interface ProductRelationshipService {

    /**
     * Retrieve a paginated list of product relationships associated with the specified product.
     */
    Mono<PaginationResponse<ProductRelationshipDTO>> getAllRelationships(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new relationship record for a specific product.
     */
    Mono<ProductRelationshipDTO> createRelationship(Long productId, ProductRelationshipDTO dto);

    /**
     * Retrieve a specific relationship record by its unique identifier, ensuring it matches the product.
     */
    Mono<ProductRelationshipDTO> getRelationship(Long productId, Long relationshipId);

    /**
     * Update an existing product relationship record associated with the specified product.
     */
    Mono<ProductRelationshipDTO> updateRelationship(Long productId, Long relationshipId, ProductRelationshipDTO dto);

    /**
     * Remove an existing product relationship record by its unique identifier.
     */
    Mono<Void> deleteRelationship(Long productId, Long relationshipId);
}