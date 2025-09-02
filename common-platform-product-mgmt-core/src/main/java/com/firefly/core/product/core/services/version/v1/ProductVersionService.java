package com.firefly.core.product.core.services.version.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.version.v1.ProductVersionDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductVersionService {

    /**
     * Retrieve a paginated list of product versions associated with the specified product.
     */
    Mono<PaginationResponse<ProductVersionDTO>> getAllProductVersions(UUID productId, PaginationRequest paginationRequest);

    /**
     * Create a new product version for a specific product.
     */
    Mono<ProductVersionDTO> createProductVersion(UUID productId, ProductVersionDTO productVersionDTO);

    /**
     * Retrieve a specific product version by its unique identifier, validating it belongs to the specified product.
     */
    Mono<ProductVersionDTO> getProductVersion(UUID productId, UUID versionId);

    /**
     * Update an existing product version record associated with the specified product.
     */
    Mono<ProductVersionDTO> updateProductVersion(UUID productId, UUID versionId, ProductVersionDTO productVersionDTO);

    /**
     * Remove an existing product version record by its unique identifier.
     */
    Mono<Void> deleteProductVersion(UUID productId, UUID versionId);
}