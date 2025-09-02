package com.firefly.core.product.core.services.core.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.core.v1.ProductDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductService {

    /**
     * Retrieve a paginated list of products.
     */
    Mono<PaginationResponse<ProductDTO>> getAllProducts(PaginationRequest paginationRequest);

    /**
     * Create a new product.
     */
    Mono<ProductDTO> createProduct(ProductDTO productDTO);

    /**
     * Retrieve a specific product by its unique identifier.
     */
    Mono<ProductDTO> getProduct(UUID productId);

    /**
     * Update an existing product by its unique identifier.
     */
    Mono<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO);

    /**
     * Delete an existing product by its unique identifier.
     */
    Mono<Void> deleteProduct(UUID productId);
}
