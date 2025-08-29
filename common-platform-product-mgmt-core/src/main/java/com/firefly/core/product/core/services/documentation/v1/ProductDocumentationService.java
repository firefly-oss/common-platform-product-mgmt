package com.firefly.core.product.core.services.documentation.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import reactor.core.publisher.Mono;

public interface ProductDocumentationService {

    /**
     * Retrieve a paginated list of all documentation items linked to a product.
     */
    Mono<PaginationResponse<ProductDocumentationDTO>> getAllDocumentations(Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new documentation item for a specific product.
     */
    Mono<ProductDocumentationDTO> createDocumentation(Long productId, ProductDocumentationDTO documentationDTO);

    /**
     * Retrieve a specific documentation item by its unique identifier within a product.
     */
    Mono<ProductDocumentationDTO> getDocumentation(Long productId, Long docId);

    /**
     * Update an existing documentation item for a specific product.
     */
    Mono<ProductDocumentationDTO> updateDocumentation(Long productId, Long docId, ProductDocumentationDTO documentationDTO);

    /**
     * Delete an existing documentation item by its unique identifier, within the context of a product.
     */
    Mono<Void> deleteDocumentation(Long productId, Long docId);
}

