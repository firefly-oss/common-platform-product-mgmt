package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import reactor.core.publisher.Mono;

public interface ProductBundleService {

    /**
     * Retrieve a specific product bundle by its unique identifier.
     */
    Mono<ProductBundleDTO> getById(Long bundleId);

    /**
     * Retrieve a paginated list of all product bundles.
     */
    Mono<PaginationResponse<ProductBundleDTO>> getAll(PaginationRequest paginationRequest);

    /**
     * Create a new product bundle.
     */
    Mono<ProductBundleDTO> create(ProductBundleDTO productBundleDTO);

    /**
     * Update an existing product bundle by its unique identifier.
     */
    Mono<ProductBundleDTO> update(Long bundleId, ProductBundleDTO productBundleDTO);

    /**
     * Delete a product bundle by its unique identifier.
     */
    Mono<Void> delete(Long bundleId);
}