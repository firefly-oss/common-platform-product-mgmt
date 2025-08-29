package com.firefly.core.product.core.services.version.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.version.v1.ProductVersionDTO;
import reactor.core.publisher.Mono;

public interface ProductVersionService {

    /**
     * Retrieve a paginated list of product versions associated with the specified product.
     */
    Mono<PaginationResponse<ProductVersionDTO>> getAllProductVersions(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new product version for a specific product.
     */
    Mono<ProductVersionDTO> createProductVersion(Long productId, ProductVersionDTO productVersionDTO);

    /**
     * Retrieve a specific product version by its unique identifier, validating it belongs to the specified product.
     */
    Mono<ProductVersionDTO> getProductVersion(Long productId, Long versionId);

    /**
     * Update an existing product version record associated with the specified product.
     */
    Mono<ProductVersionDTO> updateProductVersion(Long productId, Long versionId, ProductVersionDTO productVersionDTO);

    /**
     * Remove an existing product version record by its unique identifier.
     */
    Mono<Void> deleteProductVersion(Long productId, Long versionId);
}