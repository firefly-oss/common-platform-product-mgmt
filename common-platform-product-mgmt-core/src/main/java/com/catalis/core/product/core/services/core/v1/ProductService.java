package com.catalis.core.product.core.services.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import reactor.core.publisher.Mono;

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
    Mono<ProductDTO> getProduct(Long productId);

    /**
     * Update an existing product by its unique identifier.
     */
    Mono<ProductDTO> updateProduct(Long productId, ProductDTO productDTO);

    /**
     * Delete an existing product by its unique identifier.
     */
    Mono<Void> deleteProduct(Long productId);
}
